package com.example.learning_centre_managment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_centre_managment.entity.LessonEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    List<LessonEntity> findLessonEntitiesByGroupIdAndModule(UUID groupId,Integer module);
    Page<LessonEntity> findAllByGroupId(Pageable pageable, UUID groupId);


    List<LessonEntity> findLessonEntitiesByModule(Integer module);

    List<LessonEntity> findLessonEntitiesByGroupId(UUID groupId);
}
