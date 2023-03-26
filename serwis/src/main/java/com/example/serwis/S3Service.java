package com.example.serwis;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service {
    @Autowired
    private AmazonS3 amazonS3;

    private final String S3Bucket = "serwiszdjecia";

    public String uploadFiletoS3(MultipartFile file) throws IOException {
        File modifiedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(modifiedFile);
        fos.write(file.getBytes());
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(S3Bucket, fileName, modifiedFile);
        modifiedFile.delete();
        return fileName;
    }

    public void deleteFileFromS3(String fileName) throws IOException {
        amazonS3.deleteObject(S3Bucket, fileName);
    }

    public ByteArrayResource downloadFileFromS3(String fileName) throws IOException {
        com.amazonaws.services.s3.model.S3Object s3Object = amazonS3.getObject(S3Bucket, fileName);
        S3ObjectInputStream objectContent = s3Object.getObjectContent();
        byte[] byteArray = IOUtils.toByteArray(objectContent);
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        return resource;
    }

}
