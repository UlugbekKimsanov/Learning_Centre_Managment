package com.example.learning_centre_managment.dto.request;

import lombok.*;
import uz.pdp.learning_centre_managment.entity.enums.LessonStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessonCR {
    private UUID groupId;
    private Integer lessonNumber;
    private LessonStatus lessonStatus;

}
