package com.emploverse.backend.util;

import com.emploverse.backend.model.Department;
import com.emploverse.backend.model.DepartmentName;
import com.emploverse.backend.model.Employee;
import com.emploverse.backend.model.Role;
import com.emploverse.backend.model.RoleName;
import com.emploverse.backend.model.User;
import com.emploverse.backend.repository.DepartmentRepository;
import com.emploverse.backend.repository.RoleRepository;
import com.emploverse.backend.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Initialize roles
        Arrays.stream(RoleName.values()).forEach(roleName -> {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        });

        // Initialize departments
        Arrays.stream(DepartmentName.values()).forEach(departmentName -> {
            if (!departmentRepository.existsByName(departmentName)) {
                Department department = Department.builder()
                        .name(departmentName)
                        .build();
                departmentRepository.save(department);
            }
        });

        // Example user creation (commented out by default)
        // Uncomment and modify as needed
        /*
         * Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
         * .orElseThrow(() -> new RuntimeException("User Role not found"));
         * Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
         * .orElseThrow(() -> new RuntimeException("Admin Role not found"));
         * 
         * Department itDepartment = departmentRepository.findByName(DepartmentName.IT)
         * .orElseThrow(() -> new RuntimeException("IT Department not found"));
         * 
         * List<String> usernames = List.of("user1", "user2", "user3");
         * for (String username : usernames) {
         * if (!userRepository.existsByUsername(username)) {
         * User user = User.builder()
         * .username(username)
         * .email(username + "@example.com")
         * .password(passwordEncoder.encode("password"))
         * .roles(Set.of(userRole, adminRole))
         * .enabled(true)
         * .build();
         * 
         * Employee employee = Employee.builder()
         * .firstName(username + "First")
         * .lastName(username + "Last")
         * .department(itDepartment)
         * .user(user)
         * .build();
         * 
         * user.setEmployee(employee);
         * userRepository.save(user);
         * }
         * }
         */
    }
}
