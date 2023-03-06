package com.arims.service.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arims.model.User;
import com.arims.model.UserRole;



public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

    List<UserRole> findAllByUser(User user);

}
