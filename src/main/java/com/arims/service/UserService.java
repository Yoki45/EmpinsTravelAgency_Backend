package com.arims.service;

import com.arims.dto.UserRegistrationDto;
import com.arims.enums.Role;
import com.arims.model.User;
import com.arims.model.UserRole;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService  {

    User save(UserRegistrationDto registrationDto);

    UserRole saveRole(UserRole role);

    void addRoleToUser(String email, Role role);

    User updateUser(User user);

    User findUserById(Long id);

    User findUser(String email);

    List<User> getUsers();

    List<Role> getRoles();

}
