package com.example.learning_centre_managment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.pdp.learning_centre_managment.entity.enums.Subject;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Subject subject;
    private String description;
    @Column(nullable = false)
    private Integer module;

}
