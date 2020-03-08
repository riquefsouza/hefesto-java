package br.com.hfs;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.event.PostConstructApplicationEvent;
import javax.inject.Inject;

import br.com.hfs.model.Role;
import br.com.hfs.model.User;
import br.com.hfs.service.RoleService;
import br.com.hfs.service.UserService;

@ApplicationScoped
public class HefestoJ2ee8ApiApplication {

	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Inject
	private RoleService roleService;
	
	@Inject
	private UserService userService;
	
	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
		log.info("Init " + this.getClass().getName());
		
		Role role1 = roleService.save(new Role("ADMIN"));
		Role role2 = roleService.save(new Role("USER"));
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(role1);
		roles.add(role2);
		
		//senha = 123456
		User user = userService.save(new User("admin", "$2a$10$nhU38YCtaWpLzTIeG/uAIeGnu7GItrvGsQAQrgsjM9hN19cGp25N6", true));

		user.setRoles(roles);
		userService.update(user);
		
		
		log.info("-------------------------------");
		for (User user1 : userService.findAll()) {
			log.info(user1.toString());
		}
		log.info("");
	}
	
	public void postConstruct(@Observes PostConstructApplicationEvent event){ 
		log.info("PostConstruct " + this.getClass().getName());
	}
	
	public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
		log.info("Destroy " + this.getClass().getName());
	}
	
}
