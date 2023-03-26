package com.example.serwis.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.serwis.entity.ImageEntity;
import com.example.serwis.entity.ProfileImageEntity;
import com.example.serwis.service.ImageService;
import com.example.serwis.service.ProfileImageService;
import com.example.serwis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class ProfileImageController {
    @Autowired
    ProfileImageService profileImageService;

    @Autowired
    UserService userService;
    @Autowired
    private AmazonS3 amazonS3;

    private final String S3Bucket = "serwiszdjecia";


    @GetMapping("/user/{id}/file/")
    public ResponseEntity<ByteArrayResource> downloadFileFromS3(@PathVariable("id") long userId) throws IOException {
        ProfileImageEntity profileImageEntity = profileImageService.getImageByUserId(userId);
        if (profileImageEntity == null) {
            com.amazonaws.services.s3.model.S3Object s3Object = amazonS3.getObject(S3Bucket, "anonymous-user-2.png");
            S3ObjectInputStream objectContent = s3Object.getObjectContent();
            byte[] byteArray = IOUtils.toByteArray(objectContent);

            ByteArrayResource resource = new ByteArrayResource(byteArray);
            return ResponseEntity.ok().contentLength(byteArray.length)
                    .body(resource);
        }
        com.amazonaws.services.s3.model.S3Object s3Object = amazonS3.getObject(S3Bucket, profileImageEntity.getImageName());
        S3ObjectInputStream objectContent = s3Object.getObjectContent();
        byte[] byteArray = IOUtils.toByteArray(objectContent);

        ByteArrayResource resource = new ByteArrayResource(byteArray);
        return ResponseEntity.ok().contentLength(byteArray.length)
                .body(resource);
    }
    @PostMapping("/user/image/")
    public void uploadFileToS3(Authentication authentication, @RequestPart(name = "file") MultipartFile file) throws IOException {
        profileImageService.addImageToUserProfile(userService.getUserIdByUsername(authentication.getName()), file);
    }
}
