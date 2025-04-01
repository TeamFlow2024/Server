package com.teamflow.controller;

import com.teamflow.dto.FileUploadResponse;
import com.teamflow.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("chatRoomId") Long chatRoomId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String fileUrl = s3Uploader.uploadFile(file, "chat/" + chatRoomId);
        return ResponseEntity.ok(new FileUploadResponse(fileUrl));
    }
}
