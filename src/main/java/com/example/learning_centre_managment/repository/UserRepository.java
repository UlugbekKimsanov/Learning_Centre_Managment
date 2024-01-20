package com.example.learning_centre_managment.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.learning_centre_managment.entity.UserEntity;
import uz.pdp.learning_centre_managment.entity.enums.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Page<UserEntity> findAllByRole(UserRole userRole,Pageable pageable);

    List<UserEntity> findByName(String name);

    Optional<UserEntity> findByEmail(String email);

}
