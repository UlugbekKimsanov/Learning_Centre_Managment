package com.example.learning_centre_managment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_centre_managment.entity.GroupEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
    boolean existsByGroupName(String groupName);
    List<GroupEntity> findAllByMentorId(UUID mentorId);

    List<GroupEntity> findAllByCourseId(UUID courseId);
}
