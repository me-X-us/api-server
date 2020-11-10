package com.mexus.homeleisure.api.image.controller;

import com.mexus.homeleisure.api.image.service.ThumbnailImageService;
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
@RequestMapping(value = "/thumbnail/{trainingId}")
public class ThumbnailImageController {
    private static final Logger logger = LoggerFactory.getLogger(ThumbnailImageController.class);
    private final ThumbnailImageService thumbnailImageService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void uploadThumbnailImage(
            @PathVariable String trainingId,
            @RequestParam("image") MultipartFile image
    ) {
        String visitorId = SecurityContextHolder.getContext().getAuthentication().getName();
        thumbnailImageService.storeThumbnailImage(visitorId, trainingId, image);
    }

    @GetMapping
    public ResponseEntity<Resource> downloadThumbnailImage(
            @PathVariable String trainingId,
            HttpServletRequest request
    ) {
        // Load file as Resource
        Resource resource = thumbnailImageService.getThumbnailImage(trainingId);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if(contentType==null)
            contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
