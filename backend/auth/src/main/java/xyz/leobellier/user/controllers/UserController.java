package xyz.leobellier.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.leobellier.user.UserEntity;
import xyz.leobellier.user.service.UserServices;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServices userServices;
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = userServices.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/me")
    public ResponseEntity<UserEntity> getCurrentUser() {
        UserEntity user = userServices.getUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/updateMe")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user) {
        UserEntity currentUser = userServices.updateCurrentUser(user);
        return ResponseEntity.ok(currentUser);
    }
}
