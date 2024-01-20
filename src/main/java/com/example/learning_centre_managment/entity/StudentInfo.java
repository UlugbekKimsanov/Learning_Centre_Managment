package com.example.learning_centre_managment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo extends BaseEntity {
    private Integer rating;
    private UUID groupId;
    @OneToOne
    private UserEntity userEntity;


}
