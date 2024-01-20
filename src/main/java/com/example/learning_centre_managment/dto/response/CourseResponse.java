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
public class CourseResponse {
    private Subject subject;
    private String description;
    private Integer module;
    private UUID id;
}
