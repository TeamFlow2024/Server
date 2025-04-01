package com.teamflow.service;

import com.teamflow.model.User; 
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    public String saveProfileImage(String username, MultipartFile image) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String imageUrl = s3Uploader.uploadFile(image, "profile");
        user.setProfile(imageUrl);
        userRepository.save(user);

        return imageUrl;
    }
}
