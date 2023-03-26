package com.example.serwis.repository;

import com.example.serwis.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileImageRepository  extends JpaRepository<ProfileImageEntity, Long> {

    @Query(value = "SELECT * FROM profile_image WHERE profile_image.user_id= :userId" , nativeQuery = true)
    ProfileImageEntity findImageByUserId(@Param("userId") Long userId);

}
