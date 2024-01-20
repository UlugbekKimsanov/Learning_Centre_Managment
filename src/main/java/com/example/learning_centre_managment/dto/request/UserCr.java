package com.example.learning_centre_managment.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class UserCr {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
//    private UserRole userRole ;
}
