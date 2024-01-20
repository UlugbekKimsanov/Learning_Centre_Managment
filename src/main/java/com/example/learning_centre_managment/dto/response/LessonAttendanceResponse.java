package com.example.learning_centre_managment.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessonAttendanceResponse {
    private LessonResponse lessonResponse;
    private List<AttendanceResponse> attendanceResponseList;
}
