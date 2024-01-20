package com.example.learning_centre_managment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.learning_centre_managment.entity.enums.Subject;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MentorInfo extends BaseEntity {
    private Integer experience;
    private Double salary;
    @Enumerated(EnumType.STRING)
    private Subject subject;
    @OneToOne
    private UserEntity userEntity;
}
