package xyz.leobellier.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.leobellier.jwt.JwtService;
import xyz.leobellier.user.UserEntity;
import xyz.leobellier.user.dto.LoginUserDto;
import xyz.leobellier.user.dto.RegisterUserDto;
import xyz.leobellier.user.entities.LoginResponse;
import xyz.leobellier.user.service.AuthenticationService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController (JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterUserDto registerUserDto){
        UserEntity user = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto){

        UserEntity registererUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(registererUser);
        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpirationIn(jwtService.getJwtExpiration());
        return ResponseEntity.ok(loginResponse);
    }
}
