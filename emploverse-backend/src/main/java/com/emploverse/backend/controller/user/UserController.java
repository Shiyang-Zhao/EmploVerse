package com.emploverse.backend.controller.user;

import com.emploverse.backend.dto.user.SignupDTO;
import com.emploverse.backend.dto.user.UserDTO;
import com.emploverse.backend.model.user.RoleName;
import com.emploverse.backend.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userService.findUserById(id);
        return userDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<UserDTO> userDTO = userService.findUserByEmail(email);
        return userDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody SignupDTO signupDTO) {
        UserDTO savedUser = userService.saveUser(signupDTO);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<Void> changeUserPassword(@PathVariable Long id, @RequestBody String newPassword) {
        userService.changeUserPassword(id, newPassword);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists-by-email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/{userId}/assign-roles")
    public ResponseEntity<Void> assignRolesToUser(@PathVariable Long userId, @RequestBody Set<RoleName> roleNames) {
        userService.assignRolesToUser(userId, roleNames);
        return ResponseEntity.ok().build();
    }
}
