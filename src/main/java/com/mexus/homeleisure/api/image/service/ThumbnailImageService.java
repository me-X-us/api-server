package com.mexus.homeleisure.api.image.service;

import com.mexus.homeleisure.api.image.data.ThumbnailImage;
import com.mexus.homeleisure.api.image.data.ThumbnailImageRepository;
import com.mexus.homeleisure.api.image.exception.CantCreateFileDirectoryException;
import com.mexus.homeleisure.api.image.exception.FileDownloadException;
import com.mexus.homeleisure.api.training.data.TrainingRepository;
import com.mexus.homeleisure.common.exception.ThisIsNotYoursException;
import com.mexus.homeleisure.configs.FileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ThumbnailImageService {

    private final Path thumbnailImageLocation;

    private final FileService fileService;

    private final ThumbnailImageRepository thumbnailImageRepository;
    private final TrainingRepository trainingRepository;

    @Autowired
    public ThumbnailImageService(FileConfig config, ThumbnailImageRepository thumbnailImageRepository, TrainingRepository trainingRepository, FileService fileService) {
        this.thumbnailImageRepository = thumbnailImageRepository;
        this.fileService = fileService;
        this.trainingRepository = trainingRepository;
        this.thumbnailImageLocation = Paths.get(config.getThumbnailDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.thumbnailImageLocation);
        } catch (Exception e) {
            throw new CantCreateFileDirectoryException(this.thumbnailImageLocation.toString(), e);
        }
    }

    public void storeThumbnailImage(String userId, String trainingId, MultipartFile file) {
        if (!trainingRepository.existsByTrainingIdAndTrainer_UserId(Long.parseLong(trainingId), userId))
            throw new ThisIsNotYoursException();
        String fileName = fileService.storeFile(file, this.thumbnailImageLocation, trainingId);

        ThumbnailImage thumbnailImage = this.thumbnailImageRepository.findByTrainingId(userId).orElse(new ThumbnailImage());
        String filePath = this.thumbnailImageLocation.resolve(fileName).toString();
        thumbnailImage.updateThumbnailImage(trainingId, filePath);
        thumbnailImageRepository.save(thumbnailImage);
    }

    public Resource getThumbnailImage(String trainingId) {
        ThumbnailImage image = this.thumbnailImageRepository.findByTrainingId(trainingId).orElseThrow(() -> new FileDownloadException(trainingId));
        return fileService.loadFile(Paths.get(image.getFilePath()));
    }
}
