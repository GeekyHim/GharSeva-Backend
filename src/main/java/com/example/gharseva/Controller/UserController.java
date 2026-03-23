package com.example.gharseva.Controller;

import com.example.gharseva.Dto.LoginRequestDto;
import com.example.gharseva.Dto.RegisterRequestDto;
import com.example.gharseva.Entity.UserEntity;
import com.example.gharseva.Enum.Roles;
import com.example.gharseva.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequestDto request) {

        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .role(Roles.TENANT) // default role
                .build();

        userService.registerUser(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDto request) {

        return userService.loginUser(request.getEmail(), request.getPassword());
    }
}
