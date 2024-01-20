package com.example.learning_centre_managment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.learning_centre_managment.entity.enums.LessonStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonResponse {
    private UUID groupId;
    private Integer lessonNumber;
    private LessonStatus lessonStatus;
    private UUID id;
    private Integer module;
}
