package com.arims.service;

import com.arims.exception.UserNotFoundException;
import com.arims.model.*;
import com.arims.repository.RoleRepository;
import com.arims.repository.UserRepository;
import com.arims.web.dto.UserRegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {



    private UserRepository userRepository;

   // private ProfileRepository profileRepository;

    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }


    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
               registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),registrationDto.getGender(),
                registrationDto.getPhone(),
                Arrays.asList());

       return userRepository.save(user);

    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public User Update(User user) {
        return userRepository.save(user);

    }

    @Override
    public User findUserById(Long id) {
        return (User)this.userRepository.findById(id).orElseThrow(() -> {
            return new UserNotFoundException("User of id  " + id + " was not found");
        });
    }




    public User updateUserProfile(User user) {
        return (User) this.userRepository.save(user);
    }



    @Override
    public User findUser(String email) {
        return userRepository.findByEmail(email);

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
        return roleRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }






}
