package com.example.learning_centre_managment.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.learning_centre_managment.dto.request.ApplicationCr;
import uz.pdp.learning_centre_managment.dto.request.AuthDto;
import uz.pdp.learning_centre_managment.dto.request.UserUpdate;
import uz.pdp.learning_centre_managment.dto.response.*;
import uz.pdp.learning_centre_managment.service.ApplicationService;
import uz.pdp.learning_centre_managment.service.CourseService;
import uz.pdp.learning_centre_managment.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final ApplicationService applicationService;
    private final UserService userService;
    private final CourseService courseService;
    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@Valid @RequestBody AuthDto dto) {
        return userService.signIn(dto);
    }

    @PermitAll
    @PutMapping("/update")
    public ResponseEntity<UserResponse> update(@RequestBody UserUpdate userUpdate,Principal principal){
        return ResponseEntity.ok(userService.update(userUpdate, principal));
    }

    @PermitAll
    @GetMapping("/me")
    public  ResponseEntity<Object> myProfile(Principal principal){
        return ResponseEntity.ok(userService.me(principal));
    }

    @PermitAll
    @GetMapping("/delete")
    public  ResponseEntity<Object> delete(Principal principal){
        return ResponseEntity.status(200).body(userService.delete(principal));
    }

    @PostMapping("/create-application")
    public ResponseEntity<ApplicationResponse> create(@RequestBody ApplicationCr applicationCR){
        return ResponseEntity.ok(applicationService.create(applicationCR));
    }

    @PermitAll
    @GetMapping("/get_by_id/{course_id}")
    public ResponseEntity<CourseResponse> findById(@PathVariable UUID course_id) {
        return courseService.findById(course_id);
    }
    @PermitAll
    @GetMapping("/get_all_course")
    public ResponseEntity<List<CourseResponse>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return courseService.getAll(page,size);
    }
}
