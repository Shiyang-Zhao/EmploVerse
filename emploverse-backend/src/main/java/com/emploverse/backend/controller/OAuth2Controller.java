// package com.emploverse.backend.controller;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/oauth2")
// public class OAuth2Controller {

//     @GetMapping("/loginSuccess")
//     public ResponseEntity<?> loginSuccess(Authentication authentication) {
//         OAuth2User user = (OAuth2User) authentication.getPrincipal();
//         // Return some basic user details
//         return ResponseEntity.ok().body("Welcome, " + user.getAttribute("name") + "!");
//     }

//     @GetMapping("/loginFailure")
//     public ResponseEntity<?> loginFailure() {
//         return ResponseEntity.badRequest().body("Login failed. Please try again.");
//     }
// }