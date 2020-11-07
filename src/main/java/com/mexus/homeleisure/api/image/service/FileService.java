package com.mexus.homeleisure.api.image.service;

import com.mexus.homeleisure.api.image.exception.FileDownloadException;
import com.mexus.homeleisure.api.image.exception.FileNameException;
import com.mexus.homeleisure.api.image.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class FileService {

    public String storeFile(MultipartFile file, Path location, String id) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        StringTokenizer tockens = new StringTokenizer(fileName);
        tockens.nextToken(".");
        String newFileName = id + "." + tockens.nextToken();
        try {
            fileName = checkFileNameAndExtension(file);
            Files.copy(file.getInputStream(), location.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileUploadException(fileName, e);
        }
        return newFileName;
    }

    private String checkFileNameAndExtension(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            throw new FileNameException(fileName);
        }
        return fileName;
    }

    public Resource loadFile(Path filePath) {
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists())
                return resource;
            else
                throw new FileDownloadException(filePath.getFileName().toString());
        } catch (MalformedURLException e) {
            throw new FileDownloadException(filePath.getFileName().toString(), e);
        }
    }
}
