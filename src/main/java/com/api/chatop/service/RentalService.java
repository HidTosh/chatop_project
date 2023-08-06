package com.api.chatop.service;

import com.api.chatop.model.Rental;
import com.api.chatop.model.User;
import com.api.chatop.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;

@Service
@Transactional
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private S3FileService s3FileService;

    public void saveRental(Rental rental, User user) {
        rentalRepository.save(createdRental(rental, user));

    }

    public Rental createdRental(Rental rental, User user) {
        Path completePath = Paths.get(rental.getPicture());
        String pictureUrl = s3FileService.uploadObject(
                s3FileService.getS3BucketName(),
                completePath.getFileName().toString(),
                new File(completePath.toString())
        );
        rental.setName(rental.getName());
        rental.setSurface(rental.getSurface());
        rental.setPrice(rental.getPrice());
        rental.setDescription(rental.getDescription());
        rental.setPicture(pictureUrl);
        rental.setOwner(user);
        rental.setCreatedAt(OffsetDateTime.now());
        rental.setUpdatedAt(OffsetDateTime.now());

        return rental;
    }

}
