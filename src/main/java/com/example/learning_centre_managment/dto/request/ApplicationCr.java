package com.example.learning_centre_managment.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationCr {
    private String name;
    private String surname;
    private String phoneNumber;
    @Email
    private String email;
    private UUID courseId;


}
