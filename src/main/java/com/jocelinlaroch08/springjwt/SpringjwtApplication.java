package com.jocelinlaroch08.springjwt;

import com.jocelinlaroch08.springjwt.domain.AppUser;
import com.jocelinlaroch08.springjwt.domain.Role;
import com.jocelinlaroch08.springjwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringjwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringjwtApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new AppUser(null, "Lionel Messi", "lionelmessi@gmail.com", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Ansu Fati", "ansufati@gmail.com", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Manu Dibango", "manudibango@gmail.com", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Richard Bona", "richardbona@gmail.com", "1234", new ArrayList<>()));

			userService.addRoleToUser("lionelmessi@gmail.com", "ROLE_USER");
			userService.addRoleToUser("lionelmessi@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("lionelmessi@gmail.com", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("ansufati@gmail.com", "ROLE_USER");
			userService.addRoleToUser("manudibango@gmail.com", "ROLE_MANAGER");
			userService.addRoleToUser("richardbona@gmail.com", "ROLE_ADMIN");
		};
	}

}
