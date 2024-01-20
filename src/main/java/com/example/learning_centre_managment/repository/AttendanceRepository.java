package com.example.learning_centre_managment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_centre_managment.entity.AttendanceEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, UUID> {
    List<AttendanceEntity> findAllByStudentId(UUID id);

    List<AttendanceEntity> findAllByGroupId(UUID groupId);
    void deleteByStudentId(UUID id);
    List<AttendanceEntity> findAllByLessonId(UUID lessonId);
}
