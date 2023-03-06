package com.arims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arims.model.User;
import com.arims.model.UserRole;


public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    List<UserRole> findAllByUser(User user);
    
    
}
