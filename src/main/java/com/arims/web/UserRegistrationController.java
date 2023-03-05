package com.arims.web;

import com.arims.dto.LoginDto;
import com.arims.dto.UserRegistrationDto;
import com.arims.enums.Role;
import com.arims.model.*;
import com.arims.service.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping (path="api/v1/")
@CrossOrigin(origins ="*")
public class UserRegistrationController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${uploadDir}")
    private String uploadFolder;
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


  
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(userService.getRoles());
    }




}

