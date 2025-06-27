package xyz.leobellier.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.leobellier.user.UserEntity;
import xyz.leobellier.user.UserRepository;
import xyz.leobellier.user.dto.LoginUserDto;
import xyz.leobellier.user.dto.RegisterUserDto;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserEntity signup(RegisterUserDto input) {
        UserEntity user = new UserEntity()
                .setEmail(input.getEmail())
                .setName(input.getFullName())
                .setPassword(passwordEncoder.encode(input.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity authenticate(LoginUserDto input) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
