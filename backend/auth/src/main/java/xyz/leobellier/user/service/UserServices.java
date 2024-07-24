package xyz.leobellier.user.service;

import org.springframework.stereotype.Service;
import xyz.leobellier.user.UserEntity;
import xyz.leobellier.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServices {

    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers(){
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
