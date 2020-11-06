package com.mexus.homeleisure.api.image.controller;

import com.mexus.homeleisure.api.image.service.ProfileImageService;
import com.mexus.homeleisure.api.user.exception.NotYourProfileException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile/{userId}/images")
public class ProfileImageController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileImageController.class);
    private final ProfileImageService profileImageService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void uploadImage(
            @PathVariable String userId,
            @RequestParam("image") MultipartFile image
    ) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(userId))
            throw new NotYourProfileException(userId);
        profileImageService.storeProfileImage(userId, image);
    }

    @GetMapping
    public ResponseEntity<Resource> downloadProfileImage(
            @PathVariable String userId,
            HttpServletRequest request
    ) {
        // Load file as Resource
        Resource resource = profileImageService.getProfileImage(userId);
        String contentType = "application/octet-stream";
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
