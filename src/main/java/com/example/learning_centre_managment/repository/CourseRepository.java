package com.example.learning_centre_managment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_centre_managment.entity.CourseEntity;
import uz.pdp.learning_centre_managment.entity.enums.Subject;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    List<CourseEntity> findBySubject(Subject subject);
}
