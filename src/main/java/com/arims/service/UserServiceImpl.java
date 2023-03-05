package com.arims.service;

import com.arims.dto.UserRegistrationDto;
import com.arims.enums.Role;
import com.arims.exception.UserNotFoundException;
import com.arims.model.*;
import com.arims.repository.UserRepository;
import com.arims.repository.UserRoleRepository;
import com.arims.util.Utils;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository,UserRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
               registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),registrationDto.getGender(),
                registrationDto.getPhone());

       return userRepository.save(user);

    }

    @Override
    public UserRole saveRole(UserRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, Role role) {

        userRepository.findOneByEmail(email).ifPresent(user->{
            UserRole  roles =new UserRole();
            roles.setUser(user);
            roles.setRole(role);
            roles.setCreationDate(Utils.getCurrentDate());
             roleRepository.save(roles);
        });
       

    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);

    }

    @Override
    public User findUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> {
            return new UserNotFoundException("User of id  " + id + " was not found");
        });
    }




    public User updateUserProfile(User user) {
        return  this.userRepository.save(user);
    }



    @Override
    public User findUser(String email) {
        return this.userRepository.findOneByEmail(email).orElseThrow(() -> {
            return new UserNotFoundException("User with email  " + email + " was not found");
        });

    }



    public Optional<User> find(Long id){

        return userRepository.findById(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }


}
