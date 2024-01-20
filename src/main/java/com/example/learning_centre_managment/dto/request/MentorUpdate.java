package com.example.learning_centre_managment.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MentorUpdate {
    private String name;
    private String surname;
    private String password;
    @Email
    private String email;
    private String phoneNumber;
    private int experience;
}
