package com.example.learning_centre_managment.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.learning_centre_managment.dto.request.ApplicationCr;
import uz.pdp.learning_centre_managment.dto.response.ApplicationResponse;
import uz.pdp.learning_centre_managment.entity.ApplicationEntity;
import uz.pdp.learning_centre_managment.entity.CourseEntity;
import uz.pdp.learning_centre_managment.entity.enums.ApplicationStatus;
import uz.pdp.learning_centre_managment.exception.DataNotFoundException;
import uz.pdp.learning_centre_managment.exception.DuplicateValueException;
import uz.pdp.learning_centre_managment.repository.ApplicationRepository;
import uz.pdp.learning_centre_managment.repository.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uz.pdp.learning_centre_managment.entity.enums.ApplicationStatus.UNCHECKED;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationResponse create(ApplicationCr applicationCR) {
        validateApplication(applicationCR);
        Optional<CourseEntity> course = courseRepository.findById(applicationCR.getCourseId());
        if (course.isEmpty()) {
            throw new DataNotFoundException("Course not found by this id " + applicationCR.getCourseId());
        } else if (applicationRepository.existsByEmail(applicationCR.getEmail()) &&
                applicationRepository.existsByCourseId(applicationCR.getCourseId())) {
            throw new DuplicateValueException("You have already sent an application for this course!");
        }
        ApplicationEntity applicationEntity = modelMapper.map(applicationCR, ApplicationEntity.class);
        applicationEntity.setStatus(UNCHECKED);
        return modelMapper.map(applicationRepository.save(applicationEntity), ApplicationResponse.class);
    }

    private void validateApplication(ApplicationCr applicationCR) {
        validateEmail(applicationCR.getEmail());
    }

    private void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
    }


    public List<ApplicationResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<ApplicationEntity> applications = applicationRepository.findAll(pageable).getContent();
        return modelMapper.map(applications, new TypeToken<List<ApplicationResponse>>(){}.getType());
    }
    public ResponseEntity<ApplicationResponse> findById(UUID applicationId) {
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new DataNotFoundException("Application not found with this id " + applicationId));
        return ResponseEntity.ok(modelMapper.map(applicationEntity,ApplicationResponse.class));
    }

    public List<ApplicationResponse> getAllByStatus(int page, int size,ApplicationStatus status) {
        Pageable pageable = PageRequest.of(page, size);
        List<ApplicationEntity> applications = applicationRepository.findAllByStatus(pageable,status).getContent();
        return modelMapper.map(applications, new TypeToken<List<ApplicationResponse>>(){}.getType());
    }
}
