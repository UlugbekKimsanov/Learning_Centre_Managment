package com.example.learning_centre_managment.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import uz.pdp.learning_centre_managment.entity.enums.Subject;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseCr {
    private Subject subject;
    private String description;
    @Column(nullable = false)
    private Integer module;
}
