package com.example.learning_centre_managment.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceEntity extends BaseEntity {



    private UUID lessonId;
    private UUID studentId;
    private UUID groupId;
    private boolean isExist;
    private String description; // sabab
    private Integer points;
}
