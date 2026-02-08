package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final List<User> data = new ArrayList<>();
    private final FileStorageService fileStorageService;
    private int idCounter = 1;
    private final String subfolder = "users/";

    // Suppose you have a Date object
    private final Date now = new Date();

    // Convert to LocalDateTime
    private final LocalDateTime localNow = now.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public UserService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public List<User> listAll() {
        return data;
    }

    public User getUserById(int id) {
        User filterUser = data.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
        if (filterUser == null) {
            throw new RuntimeException("USER_NOT_FOUND");
        }
        return filterUser;
    }

    public void create(User formUser, MultipartFile image) {

        formUser.setId(idCounter++);
        formUser.setCreatedAt(localNow);
        formUser.setUpdatedAt(localNow);

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.storeImage(image, subfolder);
            formUser.setImagePath(imagePath);
        } else {
            formUser.setImagePath("");
        }

        data.add(formUser);
    }

    public void update(int id, User formUser, MultipartFile image) {
        User user = this.getUserById(id);

        if (isNotEmpty(formUser.getFullName())) {
            user.setFullName(formUser.getFullName());
        }

        if (isNotEmpty(formUser.getUsername())) {
            user.setUsername(formUser.getUsername());
        }

        if (isNotEmpty(formUser.getEmail())) {
            user.setEmail(formUser.getEmail());
        }

        if (isNotEmpty(formUser.getPhone())) {
            user.setPhone(formUser.getPhone());
        }

        if (formUser.getRole() != null) {
            user.setRole(formUser.getRole());
        }

        if (formUser.getStatus() != null) {
            user.setStatus(formUser.getStatus());
        }

        if (image != null && !image.isEmpty()) {

            if (user.getImagePath() != null) {
                fileStorageService.deleteImage(user.getImagePath(), subfolder);
            }

            String imagePath = fileStorageService.storeImage(image, subfolder);
            user.setImagePath(imagePath);
        }

        user.setUpdatedAt(LocalDateTime.now());
    }

    public void delete(int id) {
        User user = this.getUserById(id);

        String imagePath = user.getImagePath();
        if (imagePath != null && !imagePath.isBlank()) {
            fileStorageService.deleteImage(imagePath, subfolder);
        }

        data.remove(user);
    }

}
