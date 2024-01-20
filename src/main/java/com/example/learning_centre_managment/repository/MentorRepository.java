package com.example.learning_centre_managment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_centre_managment.entity.MentorInfo;
import uz.pdp.learning_centre_managment.entity.enums.Subject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MentorRepository extends JpaRepository<MentorInfo, UUID> {
    Optional<MentorInfo> findMentorInfoByUserEntityId(UUID uuid);
    List<MentorInfo> findAllBySubject(Subject subject);
}
