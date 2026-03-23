package com.example.gharseva.Entity;

import com.example.gharseva.Enum.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
//    @Size(min = 10, max = 10)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;
//    private Roles role =  Roles.TENANT;
}
