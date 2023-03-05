package com.arims.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arims.model.User;
import com.arims.service.UserService;
import com.arims.service.role.RoleService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> appUser = userService.findOneByEmail(username);
        if (appUser.isPresent()) {
            List<SimpleGrantedAuthority> grantedAuthorities = roleService.findAllByUser(appUser.get())
                    .stream().map(r -> r.getRole().toString()).map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(appUser.get().getEmail(), appUser.get().getPassword(), grantedAuthorities);

        } else {

            throw new UsernameNotFoundException("Invalid user");
        }

    }

}
