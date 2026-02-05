package com.pos.dashboardmvc.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String baseDir;

    public String storeImage(MultipartFile file, String subDir) {
        try {
            String uploadDir = baseDir + "/" + subDir;
            Files.createDirectories(Paths.get(uploadDir)); // Create if not exist
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename(); // ensure not to duplicate of file name
            Path filePath = Paths.get(uploadDir + fileName); // uploads/12345_example.png
            Files.write(filePath, file.getBytes()); // save image to dir

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}
