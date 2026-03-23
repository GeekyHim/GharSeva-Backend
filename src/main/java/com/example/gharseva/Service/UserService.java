package com.example.gharseva.Service;

import com.example.gharseva.Entity.UserEntity;
import com.example.gharseva.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    // REGISTER USER
    public UserEntity registerUser(UserEntity user) {

        // 1. Check duplicate email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // 2. Encrypt password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Save user
        return userRepository.save(user);
    }

    // GET USER BY ID
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String loginUser(String email, String password) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return "Login successful";
    }
}
