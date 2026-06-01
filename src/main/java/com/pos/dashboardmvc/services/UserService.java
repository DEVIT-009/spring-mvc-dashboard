package com.pos.dashboardmvc.services;

import com.pos.dashboardmvc.models.Role;
import com.pos.dashboardmvc.models.User;
import com.pos.dashboardmvc.repositories.RoleRepository;
import com.pos.dashboardmvc.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final String subfolder = "users/";

    public UserService(
            UserRepository userRepository,
            FileStorageService fileStorageService,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("USER_NOT_FOUND"));
    }

    public User create(User formUser, MultipartFile image) {

        formUser.setCreatedAt(LocalDateTime.now());
        formUser.setUpdatedAt(LocalDateTime.now());

        if (image != null && !image.isEmpty()) {
            String imagePath =
                    fileStorageService.storeImage(image, subfolder);
            formUser.setImagePath(imagePath);
        } else {
            formUser.setImagePath("");
        }

        return userRepository.save(formUser);
    }

    public User update(
            int id,
            User formUser,
            Integer roleId,
            MultipartFile image
    ) {

        User user = getUserById(id);

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

        if (roleId != null) {

            Role role = roleRepository
                    .findById(roleId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Role not found"
                            ));

            user.setRoles(Set.of(role));
        }

        if (formUser.getStatus() != null) {
            user.setStatus(formUser.getStatus());
        }

        if (isNotEmpty(formUser.getPassword())) {
            user.setPassword(formUser.getPassword());
        }

        if (image != null && !image.isEmpty()) {

            if (user.getImagePath() != null &&
                    !user.getImagePath().isBlank()) {

                fileStorageService.deleteImage(
                        user.getImagePath(),
                        subfolder
                );
            }

            String imagePath =
                    fileStorageService.storeImage(
                            image,
                            subfolder
                    );

            user.setImagePath(imagePath);
        }

        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public void delete(int id) {

        User user = getUserById(id);

        if (user.getImagePath() != null &&
                !user.getImagePath().isBlank()) {

            fileStorageService.deleteImage(
                    user.getImagePath(),
                    subfolder
            );
        }

        userRepository.delete(user);
    }
}