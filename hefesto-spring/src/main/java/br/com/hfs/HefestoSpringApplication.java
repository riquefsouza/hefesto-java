package br.com.hfs;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.hfs.model.Role;
import br.com.hfs.model.User;
import br.com.hfs.repository.RoleRepository;
import br.com.hfs.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.hfs.repository")
public class HefestoSpringApplication {

	private static final Logger log = LoggerFactory.getLogger(HefestoSpringApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(HefestoSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository, RoleRepository roleRepository) {
		return (args) -> {

			Role role1 = roleRepository.saveAndFlush(new Role("ADMIN"));
			Role role2 = roleRepository.saveAndFlush(new Role("USER"));
			
			Set<Role> roles = new HashSet<Role>();
			roles.add(role1);
			roles.add(role2);
			
			//senha = 123456
			User user = repository.saveAndFlush(new User("admin", "$2a$10$nhU38YCtaWpLzTIeG/uAIeGnu7GItrvGsQAQrgsjM9hN19cGp25N6", true));

			user.setRoles(roles);
			repository.saveAndFlush(user);
			
			
			log.info("-------------------------------");
			for (User user1 : repository.findAll()) {
				log.info(user1.toString());
			}
			log.info("");
		};
	}
}
