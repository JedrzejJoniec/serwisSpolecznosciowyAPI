package com.example.posts;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class S3Controller {
    @Autowired
    private AmazonS3 amazonS3;

    private final String S3Bucket = "serwiszdjecia";

    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam(name = "file")MultipartFile file) throws IOException {
        File modifiedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(modifiedFile);
        fos.write(file.getBytes());
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(S3Bucket, fileName, modifiedFile);
        modifiedFile.delete();
        return "success" + fileName;
    }
    @GetMapping("/file/download")
    public ResponseEntity<ByteArrayResource> downloadFileFromS3(@RequestParam(name = "file") String fileName) throws IOException {
        System.out.println("eloo");
        com.amazonaws.services.s3.model.S3Object s3Object = amazonS3.getObject(S3Bucket, fileName);
        S3ObjectInputStream objectContent = s3Object.getObjectContent();
        byte[] byteArray = IOUtils.toByteArray(objectContent);

        ByteArrayResource resource = new ByteArrayResource(byteArray);
        return ResponseEntity.ok().contentLength(byteArray.length)
                .body(resource);
    }
    @DeleteMapping("/delete/file")
    public String deleteFileFromS3(@RequestParam(name = "file") String fileName) {
        amazonS3.deleteObject(S3Bucket, fileName);
        return "File deleted succesfully" + fileName;
    }
}
