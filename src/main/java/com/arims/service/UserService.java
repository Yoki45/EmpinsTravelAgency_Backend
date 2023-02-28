package com.arims.service;

import com.arims.model.Role;
import com.arims.model.User;
import com.arims.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);

    Role saveRole(Role role);
    void  addRoleToUser (String email,String roleName);


    User Update(User user);

    User findUserById(Long id);



    User findUser (String email);



    List<User> getUsers();

    List<Role> getRoles();

}
