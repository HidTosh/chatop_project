package com.api.chatop.service;

import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private S3FileService s3FileService;

    /* Update or save rental */
    public void createRental(MultipartFile file, Map<String, String> rentalData, User user) {
        String pictureUrl = getUrlFile(file);
        Rental rental = new Rental();
        if (pictureUrl != null) {
            rental.setPicture(pictureUrl);
            rental.setOwner(user);
            rental.setCreated_at(OffsetDateTime.now());
            rental.setUpdated_at(OffsetDateTime.now());
            rental.setName(rentalData.get("name"));
            rental.setSurface(new BigDecimal(rentalData.get("surface")));
            rental.setPrice(new BigDecimal(rentalData.get("price")));
            rental.setDescription(rentalData.get("description"));

            rentalRepository.save(rental);
        }
    }

    /* Update or save rental */
    public void updateRental(Rental rental, User user) {
        boolean isRentalPresent = rentalRepository.findById(rental.getId()).isPresent();
        if (isRentalPresent) {
            Rental rentalOldData = rentalRepository.findById(rental.getId()).get();
            rental.setPicture(rentalOldData.getPicture());
            rental.setOwner(user);
            rental.setUpdated_at(OffsetDateTime.now());
            rental.setCreated_at(rentalOldData.getCreated_at());
            rentalRepository.save(rental);
        }
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

    private File convertMultiPartToFile(MultipartFile file ) throws IOException {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() ); // no need to write file delete
        fos.close(); // no need to write file delete
        return convFile;
    }

    /* check if valide url or upload file to s3 */
    private String getUrlFile(MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = file.getName();
            if (isValideFile(fileName)) {
                return s3FileService.uploadObject(
                        s3FileService.getS3BucketName(),
                        fileName,
                        file
                );
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* check if valide extension  */
    private Boolean isValideFile(String fileName) {
        List<String> allowedFileExtensions = new ArrayList<>(Arrays.asList("png", "jpg", "jpeg"));
        return allowedFileExtensions.contains(FilenameUtils.getExtension(fileName));
    }
}
