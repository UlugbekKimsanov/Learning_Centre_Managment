package com.example.learning_centre_managment.dto.response;


import lombok.*;
import uz.pdp.learning_centre_managment.entity.enums.ApplicationStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationResponse {


    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private ApplicationStatus status;
    private UUID courseId;
    private UUID id;


}
