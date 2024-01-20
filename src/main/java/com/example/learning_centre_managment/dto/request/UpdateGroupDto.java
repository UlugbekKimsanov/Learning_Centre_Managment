package com.example.learning_centre_managment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UpdateGroupDto {
    @NotNull
    private String groupName;
    @NotNull
    private UUID mentorId;

}
