package com.teamflow.controller;

import com.teamflow.dto.FileUploadResponse;
import com.teamflow.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/image")
    public ResponseEntity<FileUploadResponse> uploadProfileImage(
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails) {

        String imageUrl = profileService.saveProfileImage(userDetails.getUsername(), image);
        return ResponseEntity.ok(new FileUploadResponse(imageUrl));
    }
}
