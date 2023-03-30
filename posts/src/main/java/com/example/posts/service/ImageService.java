package com.example.posts.service;

import com.example.posts.S3Service;
import com.example.posts.entity.ImageEntity;
import com.example.posts.entity.PostEntity;
import com.example.posts.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    @Autowired
    S3Service s3Service;
    @Autowired
    ImageRepository imageRepository;


    public void deleteImage(ImageEntity image) throws IOException {
        if (image != null) {
            imageRepository.delete(image);
        }
    }
    public void changePostImage(long postId, MultipartFile newImage) throws IOException {
        ImageEntity currentImage = getImageByPostId(postId);
        String newImageName = s3Service.uploadFiletoS3(newImage);
        imageRepository.changePostImage(postId, newImageName);
    }
    public ImageEntity getImageByPostId(long postId) throws IOException {
        return imageRepository.findImageByPostId(postId);
    }
    public void addImageToPost(PostEntity post, MultipartFile multipartFile) throws IOException {
        String fileName = s3Service.uploadFiletoS3(multipartFile);
        imageRepository.save(new ImageEntity(post, fileName));
    }
    public boolean checkIfPostHasImage(long postId) {
        return imageRepository.findIfPostHasImage(postId) > 0;
    }
}
