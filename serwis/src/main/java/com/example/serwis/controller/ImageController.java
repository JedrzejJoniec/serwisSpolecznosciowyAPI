package com.example.serwis.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.serwis.entity.ImageEntity;
import com.example.serwis.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController {
    @Autowired
    ImageService imageService;
    @Autowired
    private AmazonS3 amazonS3;

    private final String S3Bucket = "serwiszdjecia";


    @GetMapping("/post/{id}/file/")
    public ResponseEntity<ByteArrayResource> downloadFileFromS3(@PathVariable("id") long postId) throws IOException {
        ImageEntity imageEntity = imageService.getImageByPostId(postId);
        com.amazonaws.services.s3.model.S3Object s3Object = amazonS3.getObject(S3Bucket, imageEntity.getImageName());
        S3ObjectInputStream objectContent = s3Object.getObjectContent();
        byte[] byteArray = IOUtils.toByteArray(objectContent);

        ByteArrayResource resource = new ByteArrayResource(byteArray);
        return ResponseEntity.ok().contentLength(byteArray.length)
                .body(resource);
    }
}
