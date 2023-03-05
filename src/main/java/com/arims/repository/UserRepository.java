package com.arims.repository;

import com.arims.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;




public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findOneByEmail(String email);

}


