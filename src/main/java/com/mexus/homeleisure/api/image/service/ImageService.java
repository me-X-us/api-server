package com.mexus.homeleisure.api.image.service;

import com.mexus.homeleisure.api.image.exception.CantCreateFileDirectoryException;
import com.mexus.homeleisure.api.image.exception.FileNameException;
import com.mexus.homeleisure.api.image.exception.FileUploadException;
import com.mexus.homeleisure.configs.FileConfig;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

  private final Path fileLocation;

  @Autowired
  public ImageService(FileConfig config) {
    this.fileLocation = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();
    try {
      Files.createDirectories(this.fileLocation);
    } catch (Exception e) {
      throw new CantCreateFileDirectoryException(this.fileLocation.toString(), e);
    }
  }

  public String storeFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    try {
      fileName = checkFileNameAndExtension(file);
      Files.copy(file.getInputStream(), fileLocation.resolve(fileName),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      throw new FileUploadException(fileName, e);
    }
    return fileName;
  }

  private String checkFileNameAndExtension(MultipartFile file) {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    if (fileName.contains("..")) {
      throw new FileNameException(fileName);
    }
    return fileName;
  }
}
