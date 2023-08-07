package com.api.chatop.service;

import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.repository.RentalProjection;
import com.api.chatop.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
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
    private S3FileService s3FileService;

    public void updateRental(Rental rental, User user) {
        String pictureUrl = getUrlFile(rental.getPicture());
        if (pictureUrl != null) {
            rental.setPicture(pictureUrl);
            rental.setOwner(user);
            rental.setUpdated_at(OffsetDateTime.now());

            rental.setCreated_at(rentalRepository.findById(rental.getId()).get().getCreated_at());


            rentalRepository.save(rental);
        }
    }

    public void saveUpdateRental(Rental rental, User user, String type) {
        String pictureUrl = getUrlFile(rental.getPicture());
        if (pictureUrl != null) {
            rental.setPicture(pictureUrl);
            rental.setOwner(user);
            rental.setUpdated_at(OffsetDateTime.now());
            OffsetDateTime creationDate = type == "new" ?
                    OffsetDateTime.now() :
                    rentalRepository.findById(rental.getId()).get().getCreated_at();

            rental.setCreated_at(creationDate);
            rentalRepository.save(rental);
        }
    }
    public List<RentalProjection> getAllRentals(){
        List<RentalProjection> rentals = new ArrayList<RentalProjection>();
        rentalRepository.findAllRentals().forEach(rental -> rentals.add(rental));
        return rentals;
    }

    public RentalProjection getRental(Integer id) {

        return rentalRepository.findRentalByID(id);
    }
    private String getUrlFile(String fileLocation) {
        String file = fileLocation.trim().toLowerCase();
        if (file.startsWith("http://") || file.startsWith("https://")) {
            return file;
        }

        Path completePath = Paths.get(fileLocation);
        String FileName = completePath.getFileName().toString();
        if (isValideFile(FileName)) {
            return s3FileService.uploadObject(
                    s3FileService.getS3BucketName(),
                    completePath.getFileName().toString(),
                    new File(completePath.toString())
            );
        }

        return null;
    }

    private Boolean isValideFile(String fileName) {
        List<String> allowedFileExtensions = new ArrayList<>(Arrays.asList("png", "jpg", "jpeg"));
        return allowedFileExtensions.contains(FilenameUtils.getExtension(fileName)) ? true : false;
    }
}
