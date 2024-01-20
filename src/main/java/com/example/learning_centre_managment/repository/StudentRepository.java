package com.example.learning_centre_managment.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_centre_managment.entity.StudentInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentInfo, UUID> {
    List<StudentInfo> findAllByGroupId(UUID groupId);

    List<StudentInfo> findAllByGroupId(UUID groupId, Sort sort  );

    Optional<StudentInfo> findStudentInfoByUserEntityId(UUID id);

}
