package com.exam;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ExamserverApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ExamserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Code");

		/*
		User user = new User();
		user.setFirstName("John");
		user.setLastName("Conan");
		user.setUsername("p4rfct0");
		user.setPassword("password");
		user.setEmail("p4rfct0@gmail.com");
		user.setProfile("default.png");
		user.setPhone("223111111");

		Role role1 = new Role();
		role1.setRoleId(1L);
		role1.setRoleName("ADMIN");

		Set<UserRole> userRoleSet = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role1);

		userRoleSet.add(userRole);

		User user1 = this.userService.createUser(user, userRoleSet);
		System.out.println(user1);

		*/
	}
}
