package com.example.learning_centre_managment.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentUpdateDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password;

}
