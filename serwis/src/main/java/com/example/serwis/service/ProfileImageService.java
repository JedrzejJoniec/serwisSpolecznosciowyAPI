package com.example.serwis.service;

import com.example.serwis.S3Service;
import com.example.serwis.entity.ImageEntity;
import com.example.serwis.entity.ProfileImageEntity;
import com.example.serwis.entity.UserEntity;
import com.example.serwis.model.Image;
import com.example.serwis.model.ProfileImage;
import com.example.serwis.repository.ImageRepository;
import com.example.serwis.repository.ProfileImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileImageService {
    @Autowired
    S3Service s3Service;
    @Autowired
    ProfileImageRepository profileImageRepository;

    @Autowired
    UserService userService;


    public ProfileImageEntity getImageByUserId(long userId) throws IOException {
        ProfileImageEntity profileImage = profileImageRepository.findImageByUserId(userId);
        return profileImage;
    }
    public void deleteProfileImage(ProfileImageEntity profileImage) throws IOException {
        //s3Service.deleteFileFromS3(profileImage.getImageName());
        profileImageRepository.delete(profileImage);
    }

    public void addImageToUserProfile(long userId, MultipartFile multipartFile) throws IOException {
        ProfileImageEntity profileImage = getImageByUserId(userId);
        if (profileImage != null) {
            deleteProfileImage(profileImage);
        }

        String fileName = s3Service.uploadFiletoS3(multipartFile);
        profileImageRepository.save(new ProfileImageEntity(userService.getUserById(userId), fileName));


    }
}
