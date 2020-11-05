package com.mexus.homeleisure.api.image.controller;

import com.mexus.homeleisure.api.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/images")
public class ImageController {

  private final ImageService imageService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void uploadImage(
      @RequestParam("image") MultipartFile image
  ) {
    imageService.storeFile(image);
  }
}
