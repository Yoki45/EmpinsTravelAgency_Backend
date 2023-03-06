package com.arims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arims.enums.Role;
import com.arims.model.User;
import com.arims.model.UserRole;
import com.arims.repository.UserRepository;
import com.arims.repository.UserRoleRepository;

@SpringBootApplication
public class ArimsApplication  implements ApplicationRunner{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(ArimsApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		if (!userRepository.existsByEmail("admin@empins.com")) {
			User user = new User();
			user.setEmail("admin@empins.com");
			user.setFirstName("admin");
			user.setLastName("super");
	
			user.setPassword(encoder.encode("!admin@2023"));
	
			user =userRepository.save(user);
	
			UserRole role = new UserRole();
	
			role.setUser(user);
			role.setRole(Role.ROLE_ADMIN);
			userRoleRepository.save(role);
	
			}
		
	}

}
