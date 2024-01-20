package com.example.learning_centre_managment.dto.request;


import jakarta.validation.constraints.Email;
import lombok.*;
import uz.pdp.learning_centre_managment.entity.enums.Subject;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MentorCr {
    private String name;
    private String surname;
    @Email
    private String email;
    private String phoneNumber;
    private Integer experience;
    private Double salary;
    private Subject subject;
}
