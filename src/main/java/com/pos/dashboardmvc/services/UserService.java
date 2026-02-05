package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private RedirectAttributes redirectAttributes;


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
        return data.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void create(User formUser, MultipartFile image) {

        formUser.setId(idCounter++);
        formUser.setCreatedAt(localNow);
        formUser.setUpdatedAt(localNow);

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.storeImage(image, "users/");
            formUser.setImagePath(imagePath);
        } else {
            formUser.setImagePath("");
        }

        data.add(formUser);
    }

    public void update(int id, User formUser, MultipartFile image) {
        User user = this.getUserById(id);

        if(user == null){
            redirectAttributes.addFlashAttribute("error", "User not found!");
            throw new RuntimeException("USER_NOT_FOUND");
        }

        if (isNotEmpty(formUser.getFullName()))
            user.setFullName(formUser.getFullName());

        if (isNotEmpty(formUser.getUsername()))
            user.setUsername(formUser.getUsername());

        if (isNotEmpty(formUser.getEmail()))
            user.setEmail(formUser.getEmail());

        if (isNotEmpty(formUser.getPhone()))
            user.setPhone(formUser.getPhone());

        if (isNotEmpty(formUser.getRole()))
            user.setRole(formUser.getRole());

        if (isNotEmpty(formUser.getStatus()))
            user.setStatus(formUser.getStatus());

        user.setUpdatedAt(localNow);

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.storeImage(image, "users/");
            user.setImagePath(imagePath);
        }
    }
}
