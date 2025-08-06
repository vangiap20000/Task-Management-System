package com.example.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import com.example.taskmanager.utils.FileUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public void saveProfile(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
	    return userRepository.findByEmail(email)
	        .orElseThrow(() -> new RuntimeException("User not found"));
	}

    public String saveAvatarFile(MultipartFile file, String oldAvatar) {
        if (file == null || file.isEmpty()) return null;
        try {
            String uploadsDir = "uploads/users/";
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) originalFilename = "avatar";
            String ext = "";
            int i = originalFilename.lastIndexOf('.');
            if (i > 0) ext = originalFilename.substring(i);
            String filename = System.currentTimeMillis() + ext;
            // Xóa avatar cũ nếu có
            if (oldAvatar != null && !oldAvatar.isEmpty()) {
                String oldPath = uploadsDir + oldAvatar.replace("users/", "");
                try { FileUtils.deleteFileIfExistsNio(oldPath); } catch (Exception ignore) {}
            }
            // Lưu file mới với tên timestamp
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadsDir);
            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }
            java.nio.file.Path filePath = uploadPath.resolve(filename);
            java.nio.file.Files.copy(file.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
