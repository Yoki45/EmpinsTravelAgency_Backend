package com.arims.web;

import com.arims.dto.UserRegistrationDto;
import com.arims.enums.Role;
import com.arims.model.*;
import com.arims.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path="api/v1/")
@CrossOrigin(origins ="*")
public class UserRegistrationController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${uploadDir}")
    private String uploadFolder;
    @Autowired
    private UserService userService;
 
    //Registration API


    @PostMapping("auth/register")
    public User register(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userService.save(userRegistrationDto);
    }

   
    // Return Username
    @GetMapping(value = "/username")
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


    @PutMapping({"/updateUserProfile"})
    public User updateProfile(@RequestBody User user) {
       return userService.updateUser(user);

    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping({"/user/{id}"})
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User newUser = this.userService.findUserById(id);
        return new ResponseEntity(newUser, HttpStatus.OK);
    }



    //Roles API

    @PostMapping("/roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

 /*   @PostMapping("/assignRole")
    public ResponseEntity<Role> AssignRole(@RequestBody addRoleToUserForm form) {
        userService.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }*/



    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(userService.getRoles());
    }




}

