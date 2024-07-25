// package com.emploverse.backend.util;

// import com.emploverse.backend.model.Employee;
// import com.emploverse.backend.model.Role;
// import com.emploverse.backend.model.RoleName;
// import com.emploverse.backend.model.User;
// import com.emploverse.backend.repository.RoleRepository;
// import com.emploverse.backend.repository.UserRepository;

// import jakarta.transaction.Transactional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;

// import java.util.Arrays;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;

// @Component
// public class DataInitializer implements CommandLineRunner {

//     @Autowired
//     private RoleRepository roleRepository;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private BCryptPasswordEncoder passwordEncoder;

//     @Override
//     @Transactional
//     public void run(String... args) throws Exception {
//         Arrays.stream(RoleName.values()).forEach(roleName -> {
//             if (!roleRepository.existsByName(roleName)) {
//                 Role role = new Role();
//                 role.setName(roleName);
//                 roleRepository.save(role);
//             }
//         });

//         Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//                 .orElseThrow(() -> new RuntimeException("User Role not found"));
//         Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
//                 .orElseThrow(() -> new RuntimeException("Admin Role not found"));

//         List<String> championNames = List.of(
//                 "Aatrox", "Ahri", "Akali", "Alistar", "Amumu", "Anivia", "Annie", "Ashe", "Aurelion Sol", "Azir",
//                 "Bard", "Blitzcrank", "Brand", "Braum", "Caitlyn", "Camille", "Cassiopeia", "Cho'Gath", "Corki",
//                 "Darius", "Diana", "Dr. Mundo", "Draven", "Ekko", "Elise", "Evelynn", "Ezreal", "Fiddlesticks", "Fiora",
//                 "Fizz", "Galio", "Gangplank", "Garen", "Gnar", "Gragas", "Graves", "Hecarim", "Heimerdinger", "Illaoi",
//                 "Irelia", "Ivern", "Janna", "Jarvan IV", "Jax", "Jayce", "Jhin", "Jinx", "Kai'Sa", "Kalista", "Karma");

//         for (int i = 0; i < 50; i++) {
//             String username = championNames.get(i);
//             String email = "s" + i + "@w.w";
//             String password = passwordEncoder.encode("123");

//             if (!userRepository.existsByUsername(username)) {
//                 User user = User.builder()
//                         .username(username)
//                         .email(email)
//                         .password(password)
//                         .roles(new HashSet<>(Set.of(userRole, adminRole)))
//                         .enabled(true)
//                         .build();

//                 Employee employee = Employee.builder()
//                         .firstName(username + "FirstName")
//                         .lastName(username + "LastName")
//                         .user(user)
//                         .build();

//                 user.setEmployee(employee);
//                 userRepository.save(user);
//             }
//         }
//     }
// }
