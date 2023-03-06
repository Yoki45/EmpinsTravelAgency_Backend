package com.arims.service.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arims.model.User;
import com.arims.model.UserRole;



@Service

public class UserRoleImpl implements RoleService {
    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public List<UserRole> findAllByUser(User user) {
        return userRoleRepo.findAllByUser(user);
    }

    @Override
    public UserRole addUserRole(UserRole userRole) {
        return userRoleRepo.save(userRole);
    }

}
