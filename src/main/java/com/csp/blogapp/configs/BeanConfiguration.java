package com.csp.blogapp.configs;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.csp.blogapp.entities.Role;
import com.csp.blogapp.repositories.RoleRepository;

@Configuration
public class BeanConfiguration implements CommandLineRunner {

	/*
	 * @Autowired private PasswordEncoder passwordEncoder;
	 */

	@Autowired
	private RoleRepository roleRepository;

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * System.out.println("----This is for Spring Security to get encoded password----");
		 * System.out.println("PasswordEncoder Code : " +
		 * passwordEncoder.encode("Amit@123"));
		 * System.out.println("---------------------------------");
		 */
		
		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_ROLE_ID);
			role1.setName(AppConstants.ADMIN_ROLE_NAME);

			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_ROLE_ID);
			role2.setName(AppConstants.NORMAL_ROLE_NAME);
			
			Role role3 = new Role();
			role3.setId(AppConstants.TESTER_ROLE_ID);
			role3.setName(AppConstants.TESTER_ROLE_NAME);

			List<Role> saveRoles = roleRepository.saveAll(List.of(role1, role2, role3));

			saveRoles.forEach(System.out::println);

		} catch (Exception e) {
			throw e;
		}
	}
}
