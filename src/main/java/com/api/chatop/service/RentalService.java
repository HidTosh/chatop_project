package com.api.chatop.service;

import com.api.chatop.dto.RequestPostRentalDto;
import com.api.chatop.dto.RequestPutRentalDto;
import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.repository.MessageRepository;
import com.api.chatop.repository.RentalRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private S3FileService s3FileService;

    @Value("${type.store.file}")
    @Getter
    private String typeStoreFile;

    @Value("${folder.apache2.storage}")
    @Getter
    private String folderApache2Storage;

    @Value("${localhost.url}")
    @Getter
    private String localhostUrl;

    private final OffsetDateTime offsetDateTime = OffsetDateTime.now();

    /* create or save rental */
    public void createRental(RequestPostRentalDto requestPostRentalDto, User user) {
        String pictureUrl = getUrlFile(requestPostRentalDto.getPicture());
        if (pictureUrl != null) {
            Rental rental = new Rental();
            rental.setName(requestPostRentalDto.getName());
            rental.setSurface(requestPostRentalDto.getSurface());
            rental.setPrice(requestPostRentalDto.getPrice());
            rental.setDescription(requestPostRentalDto.getDescription());
            rental.setPicture(pictureUrl);
            rental.setOwner(user);
            rental.setCreated_at(offsetDateTime);
            rental.setUpdated_at(offsetDateTime);
            rentalRepository.save(rental);
        }
    }

    /* Update or save rental */
    public void updateRental(RequestPutRentalDto requestPutRentalDto) {
        if (getRental(requestPutRentalDto.getId()) != null) {
            Rental rental = getRental(requestPutRentalDto.getId());
            rental.setName(requestPutRentalDto.getName());
            rental.setPrice(requestPutRentalDto.getPrice());
            rental.setSurface(requestPutRentalDto.getSurface());
            rental.setDescription(requestPutRentalDto.getDescription());
            rental.setUpdated_at(offsetDateTime);
            rentalRepository.save(rental);
        }
    }

    /* delete rental */
    public String deleteRental(Integer id, User user) {
        Rental rental = getRental(id);
        if (rental.getOwner_id().equals(user.getId())) {
            String url = rental.getPicture();
            deleteFile(url.substring(url.lastIndexOf('/') + 1));
            messageRepository.deleteByRentalId(id);
            rentalRepository.deleteById(id);
            return "Rental and related Messages are deleted";
        }
        return "Not authorized to delete";
    }

    /* get all rental */
    public List<Rental> getAllRentals(){
        List<Rental> rentals = new ArrayList<Rental>();
        rentalRepository.findAll().forEach(rental -> rentals.add(rental));
        return rentals;
    }

    /* get rental by id */
    public Rental getRental(Integer id) {
        if (rentalRepository.findById(id).isPresent()) {
            return rentalRepository.findById(id).get();
        }
        return null;
    }


    /* delete file on S3 */
    private String deleteFile(final String fileName) {
        try {
            if (typeStoreFile.equals("aws-s3")) {
                s3FileService.deleteObject(s3FileService.getS3BucketName(), fileName);
            } else if (typeStoreFile.equals("local-apache")) {
                String filePath = folderApache2Storage + fileName;
                Files.delete(Paths.get(filePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Deleted File: " + fileName;
    }

    /* upload file to S3 */
    private String getUrlFile(MultipartFile multipartFile) {
        try {
            if (typeStoreFile.equals("aws-s3")) {
                File file = convertMultiPartToFile(multipartFile, "");
                String fileName = offsetDateTime.toEpochSecond() + file.getName();
                if (isValideFile(fileName)) {
                    String urlPicture = s3FileService.uploadObject(
                            s3FileService.getS3BucketName(),
                            fileName,
                            file
                    );
                    file.delete();
                    return urlPicture;
                }
            } else if (typeStoreFile.equals("local-apache")) {
                File file = convertMultiPartToFile(multipartFile, folderApache2Storage);
                return localhostUrl + file.getName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* put file on S3 bucket */
    private File convertMultiPartToFile(MultipartFile file, String pathStore) throws IOException {
        File convFile = new File(pathStore + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    /* check if valid extension  */
    private Boolean isValideFile(String fileName) {
        List<String> allowedFileExtensions = new ArrayList<>(Arrays.asList("png", "jpg", "jpeg"));
        return allowedFileExtensions.contains(FilenameUtils.getExtension(fileName));
    }
}
