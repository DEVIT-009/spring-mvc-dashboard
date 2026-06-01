package com.pos.dashboardmvc.utils.seeder;

import com.pos.dashboardmvc.models.Permission;
import com.pos.dashboardmvc.models.Role;
import com.pos.dashboardmvc.repositories.PermissionRepository;
import com.pos.dashboardmvc.repositories.RoleRepository;
import com.pos.dashboardmvc.shared.constants.PermissionConstants;
import com.pos.dashboardmvc.shared.constants.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class RolePermissionSeeder {

    @Bean
    @Order(1)
    CommandLineRunner seedRolesAndPermissions(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository
    ) {

        return args -> {

            // DASHBOARD
            Permission dashboardView = createPermission(
                    permissionRepository,
                    PermissionConstants.DASHBOARD_VIEW,
                    "View dashboards"
            );

            Permission reportView = createPermission(
                    permissionRepository,
                    PermissionConstants.REPORT_VIEW,
                    "View reports"
            );

            Permission analystView = createPermission(
                    permissionRepository,
                    PermissionConstants.ANALYST_VIEW,
                    "View analyst"
            );

            // USER
            Permission userView = createPermission(
                    permissionRepository,
                    PermissionConstants.USER_VIEW,
                    "View users"
            );

            Permission userCreate = createPermission(
                    permissionRepository,
                    PermissionConstants.USER_CREATE,
                    "Create users"
            );

            Permission userUpdate = createPermission(
                    permissionRepository,
                    PermissionConstants.USER_UPDATE,
                    "Update users"
            );

            Permission userDelete = createPermission(
                    permissionRepository,
                    PermissionConstants.USER_DELETE,
                    "Delete users"
            );

            // SUBJECT
            Permission subjectView = createPermission(
                    permissionRepository,
                    PermissionConstants.SUBJECT_VIEW,
                    "View subjects"
            );

            Permission subjectCreate = createPermission(
                    permissionRepository,
                    PermissionConstants.SUBJECT_CREATE,
                    "Create subjects"
            );

            Permission subjectUpdate = createPermission(
                    permissionRepository,
                    PermissionConstants.SUBJECT_UPDATE,
                    "Update subjects"
            );

            Permission subjectDelete = createPermission(
                    permissionRepository,
                    PermissionConstants.SUBJECT_DELETE,
                    "Delete subjects"
            );

            // TEACHER
            Permission teacherView = createPermission(
                    permissionRepository,
                    PermissionConstants.TEACHER_VIEW,
                    "View teachers"
            );

            Permission teacherCreate = createPermission(
                    permissionRepository,
                    PermissionConstants.TEACHER_CREATE,
                    "Create teachers"
            );

            Permission teacherUpdate = createPermission(
                    permissionRepository,
                    PermissionConstants.TEACHER_UPDATE,
                    "Update teachers"
            );

            Permission teacherDelete = createPermission(
                    permissionRepository,
                    PermissionConstants.TEACHER_DELETE,
                    "Delete teachers"
            );

            // STUDENT
            Permission studentView = createPermission(
                    permissionRepository,
                    PermissionConstants.STUDENT_VIEW,
                    "View students"
            );

            Permission studentCreate = createPermission(
                    permissionRepository,
                    PermissionConstants.STUDENT_CREATE,
                    "Create students"
            );

            Permission studentUpdate = createPermission(
                    permissionRepository,
                    PermissionConstants.STUDENT_UPDATE,
                    "Update students"
            );

            Permission studentDelete = createPermission(
                    permissionRepository,
                    PermissionConstants.STUDENT_DELETE,
                    "Delete students"
            );

            createRole(
                    roleRepository,
                    RoleConstants.ADMIN,
                    "System Administrator",
                    Set.of(
                            dashboardView,
                            reportView,
                            analystView,

                            userView,
                            userCreate,
                            userUpdate,
                            userDelete,

                            subjectView,
                            subjectCreate,
                            subjectUpdate,
                            subjectDelete,

                            teacherView,
                            teacherCreate,
                            teacherUpdate,
                            teacherDelete,

                            studentView,
                            studentCreate,
                            studentUpdate,
                            studentDelete
                    )
            );

            createRole(
                    roleRepository,
                    RoleConstants.MANAGER,
                    "Store Manager",
                    Set.of(
                            dashboardView,
                            reportView,
                            analystView
                    )
            );

            createRole(
                    roleRepository,
                    RoleConstants.USER,
                    "System User",
                    Set.of(
                            dashboardView
                    )
            );

            System.out.println("✅ Roles and permissions seeded");
        };
    }

    private Permission createPermission(
            PermissionRepository repository,
            String name,
            String description
    ) {

        return repository.findByName(name)
                .orElseGet(() -> {

                    Permission permission = new Permission();

                    permission.setName(name);
                    permission.setDescription(description);

                    return repository.save(permission);
                });
    }

    private void createRole(
            RoleRepository repository,
            String name,
            String description,
            Set<Permission> permissions
    ) {

        Role role = repository
                .findByName(name)
                .orElseGet(Role::new);

        role.setName(name);
        role.setDescription(description);

        if (permissions != null && !permissions.isEmpty()) {
            role.setPermissions(permissions);
        }

        repository.save(role);
    }

}
