package com.example.learning_centre_managment.dto.request;

import lombok.*;

import java.util.UUID;
//@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StudentCR {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private UUID groupId;
}
