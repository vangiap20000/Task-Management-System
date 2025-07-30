package com.example.taskmanager.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUtils {

    private static String baseUrl;

    @Value("${app.base-url}")
    public void setBaseUrl(String baseUrl) {
        FileUtils.baseUrl = baseUrl;
    }

    public static String buildPhotoUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        return baseUrl + fileName;
    }

    public static String uploadFile(MultipartFile file, String uploadDir) throws IOException {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();

        } catch (IOException e) {
            throw new IOException("Failed to upload file: " + e.getMessage());
        }
    }

    public static boolean deleteFileIfExistsNio(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        
        return Files.deleteIfExists(path);
    }
}
