package com.example.learning_centre_managment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.learning_centre_managment.entity.enums.Subject;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class MentorResponse {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private Integer experience;
    private Subject subject;
    private double salary;
//    private Double salary;
//    private Subject subject;
    private UUID id;

}
