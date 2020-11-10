package com.mexus.homeleisure.api.image.service;

import com.mexus.homeleisure.api.image.data.ProfileImage;
import com.mexus.homeleisure.api.image.data.ProfileImageRepository;
import com.mexus.homeleisure.api.image.exception.CantCreateFileDirectoryException;
import com.mexus.homeleisure.api.image.exception.FileDownloadException;
import com.mexus.homeleisure.configs.FileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProfileImageService {

    private final Path profileImageLocation;

    private final FileService fileService;

    private final ProfileImageRepository profileImageRepository;

    @Autowired
    public ProfileImageService(FileConfig config, ProfileImageRepository profileImageRepository, FileService fileService) {
        this.profileImageRepository = profileImageRepository;
        this.fileService = fileService;
        this.profileImageLocation = Paths.get(config.getProfileDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.profileImageLocation);
        } catch (Exception e) {
            throw new CantCreateFileDirectoryException(this.profileImageLocation.toString(), e);
        }
    }

    public void storeProfileImage(String userId, MultipartFile file) {
        String fileName = fileService.storeFile(file, this.profileImageLocation, userId);

        ProfileImage profileImage = this.profileImageRepository.findByUserId(userId).orElse(new ProfileImage());
        String filePath = this.profileImageLocation.resolve(fileName).toString();
        profileImage.updateProfileImage(userId, filePath);
        profileImageRepository.save(profileImage);
    }

    public Resource getProfileImage(String userId) {
        ProfileImage image = this.profileImageRepository.findByUserId(userId).orElseThrow(() -> new FileDownloadException(userId));
        return fileService.loadFile(Paths.get(image.getFilePath()));
    }
}
