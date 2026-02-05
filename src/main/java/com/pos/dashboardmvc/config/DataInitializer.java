package com.pos.dashboardmvc.config;

import com.pos.dashboardmvc.models.User;
import com.pos.dashboardmvc.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class DataInitializer {

    // Suppose you have a Date object
    private final Date now = new Date();

    // Convert to LocalDateTime
    private final LocalDateTime localNow = now.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    @Bean
    CommandLineRunner seedUsers(UserService userService) {
        return args -> {

            if (userService.listAll().isEmpty()) {

                User admin = new User();
                admin.setFullName("System Administrator");
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPhone("012345678");
                admin.setRole("ADMIN");
                admin.setStatus("ACTIVE");
                admin.setImagePath("");
                admin.setCreatedAt(localNow);

                User staff = new User();
                staff.setFullName("Staff User");
                staff.setUsername("staff");
                staff.setEmail("staff@example.com");
                staff.setPhone("098765432");
                staff.setRole("STAFF");
                staff.setStatus("INACTIVE");
                staff.setImagePath("");
                staff.setCreatedAt(localNow);

                userService.create(admin, null);
                userService.create(staff, null);

                System.out.println("✅ 2 default users created");
            }
        };
    }
}
