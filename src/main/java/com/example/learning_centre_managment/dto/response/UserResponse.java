package com.example.learning_centre_managment.dto.response;

import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private UUID id;
}
