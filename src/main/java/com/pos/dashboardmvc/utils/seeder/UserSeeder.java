package com.pos.dashboardmvc.utils.seeder;

import com.pos.dashboardmvc.models.Role;
import com.pos.dashboardmvc.models.User;
import com.pos.dashboardmvc.repositories.RoleRepository;
import com.pos.dashboardmvc.repositories.UserRepository;
import com.pos.dashboardmvc.services.UserService;
import com.pos.dashboardmvc.shared.constants.RoleConstants;
import com.pos.dashboardmvc.shared.enums.UserStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@Configuration
public class UserSeeder {

    // Suppose you have a Date object
    private final Date now = new Date();
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    UserSeeder(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository
    ){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // Convert to LocalDateTime
    private final LocalDateTime localNow = now.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    @Bean
    @Order(2)
    @DependsOn("seedRolesAndPermissions")
    CommandLineRunner seedUsers(UserService userService, RoleRepository roleRepository) {
        return args -> {

            roleRepository.findAll()
                    .forEach(r -> System.out.println(r.getName()));

            Role adminRole = roleRepository
                    .findByName(RoleConstants.ADMIN)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Role not found: " + RoleConstants.ADMIN
                            ));

            Role userRole = roleRepository
                    .findByName(RoleConstants.USER)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Role not found: " + RoleConstants.USER
                            ));

            if (!(userRepository.existsByUsername("admin"))) {
                User admin = new User();
                admin.setFullName("System Administrator");
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPhone("012345678");
                admin.setRoles(Set.of(adminRole));
                admin.setStatus(UserStatus.ACTIVE);
                admin.setImagePath("");
                admin.setCreatedAt(localNow);
                admin.setPassword(
                        passwordEncoder.encode("password")
                );

                userService.create(admin, null);
                System.out.println("/// ADMIN Created.");
            }

            if (!(userRepository.existsByUsername("user"))) {
                User user = new User();
                user.setFullName("User");
                user.setUsername("user");
                user.setEmail("user@example.com");
                user.setPhone("098765432");
                user.setRoles(Set.of(userRole));
                user.setStatus(UserStatus.ACTIVE);
                user.setImagePath("");
                user.setCreatedAt(localNow);
                user.setPassword(
                    passwordEncoder.encode("password")
                );
                userService.create(user, null);
                System.out.println("/// USER Created.");
            }

        };
    }
}
