package com.rehancode.library_ms.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.library_ms.Entity.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByEmail(String email);
     Optional<UserModel> findByName(String name);
     Boolean existsByName(String name);
     Boolean existsByEmail(String email);

}

