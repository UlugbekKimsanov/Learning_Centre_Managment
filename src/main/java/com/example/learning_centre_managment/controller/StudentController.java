package com.example.learning_centre_managment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.learning_centre_managment.dto.response.*;
import uz.pdp.learning_centre_managment.service.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/get_students_by_rating")
    public ResponseEntity<List<StudentResponse>> getStudentByRating(Principal principal){
        return ResponseEntity.status(200).body(studentService.getStudentByRating(principal));
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my-attendance")
    public  ResponseEntity<List<LessonAttendanceResponse>> studentAttendances(Principal principal){
        return ResponseEntity.ok(attendanceService.getStudentAttendances(UUID.fromString(principal.getName())));
    }



}
