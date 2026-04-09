package com.example.gharseva.Service;

import com.example.gharseva.Dto.AuthResponseDto;
import com.example.gharseva.Dto.LoginRequestDto;
import com.example.gharseva.Dto.RegisterRequestDto;
import com.example.gharseva.Enum.Roles;
import com.example.gharseva.Exception.BadRequestException;
import com.example.gharseva.Exception.ConflictException;
import com.example.gharseva.Exception.ResourceNotFoundException;
import com.example.gharseva.Entity.UserEntity;
import com.example.gharseva.Repository.UserRepository;
import com.example.gharseva.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDto registerUser(RegisterRequestDto request) {
        String normalizedEmail = normalizeEmail(request.getEmail());

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ConflictException("Email already registered");
        }

        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Roles.TENANT)
                .build();

        UserEntity savedUser = userRepository.save(user);

        return AuthResponseDto.builder()
                .message("User registered successfully")
                .status("SUCCESS")
                .userId(savedUser.getId())
                .token(null)
                .build();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public AuthResponseDto loginUser(LoginRequestDto request) {
        String normalizedEmail = normalizeEmail(request.getEmail());

        UserEntity user = userRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        return AuthResponseDto.builder()
                .message("Login successful")
                .status("SUCCESS")
                .userId(user.getId())
                .token(jwtUtil.generateToken(user))
                .build();
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
