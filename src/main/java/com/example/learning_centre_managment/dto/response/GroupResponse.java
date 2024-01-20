package com.example.learning_centre_managment.dto.response;

import lombok.*;
import uz.pdp.learning_centre_managment.entity.enums.GroupStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupResponse {
    private String groupName;
    private Integer studentCount;
    private UUID courseId;
    private UUID mentorId;
    private GroupStatus status;
    private UUID id;
    private Integer module;
}
