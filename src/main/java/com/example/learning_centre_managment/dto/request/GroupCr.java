package com.example.learning_centre_managment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupCr {
    @NotBlank(message = "must not empty")
    private String groupName;
    @NotNull(message = "must not null")
    private UUID courseId;
    @NotNull(message = "must not null")
    private UUID mentorId;
}
