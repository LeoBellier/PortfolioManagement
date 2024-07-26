package xyz.leobellier.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        if (users.isEmpty()) {return ResponseEntity.status(204).body(users);}
        return ResponseEntity.ok(users);
    }
}
