package com.arims.web;

import com.arims.model.*;
import com.arims.service.*;
import com.arims.web.dto.LoginDto;
import com.arims.web.dto.UserRegistrationDto;
import com.arims.web.jwt.JwtProvider;
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
    @Autowired
    JwtProvider jwtProvider;

    private UserService userService;

    private User user;



    @Autowired
    private AuthenticationManager authenticationManager;


    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
        this.user = user;
    }


    //Registration API


    @PostMapping("auth/register")
    public User register(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userService.save(userRegistrationDto);
    }

    @PostMapping("auth/login")
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginDto loginDto) {
        HashMap map = new HashMap();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            //  return loginDto;

            String jwt = jwtProvider.generateJwtToken(authentication);
//        return ResponseEntity.ok(new JwtResponse(jwt));

            map.put("success", true);
            map.put("accessToken", jwt);

        } catch (AuthenticationException e) {
            map.put("success", false);
            map.put("message", "Wrong username or password!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // Return Username
    @GetMapping(value = "/username")
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


    @PutMapping({"/updateUserProfile"})
    public User updateProfile(@RequestBody User user) {
       return userService.Update(user);

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

    @PostMapping("/assignRole")
    public ResponseEntity<Role> AssignRole(@RequestBody addRoleToUserForm form) {
        userService.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(userService.getRoles());
    }




}

        @Data
        class addRoleToUserForm {
            String userName;
            String roleName;

}

