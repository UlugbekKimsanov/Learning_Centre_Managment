package com.example.learning_centre_managment.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserUpdate {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
}
