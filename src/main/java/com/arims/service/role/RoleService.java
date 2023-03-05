package com.arims.service.role;

import java.util.List;

import com.arims.model.User;
import com.arims.model.UserRole;



public interface RoleService {

    List<UserRole> findAllByUser(User user);

    UserRole addUserRole(UserRole userRole);

}
