package br.com.hfs;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.event.PostConstructApplicationEvent;
import javax.inject.Inject;

import br.com.hfs.model.AdmProfile;
import br.com.hfs.model.AdmUser;
import br.com.hfs.service.AdmProfileService;
import br.com.hfs.service.AdmUserService;

@ApplicationScoped
public class HefestoJ2ee8ApiApplication {

	private Logger log = LogManager.getLogger(HefestoJ2ee8ApiApplication.class);
	
	@Inject
	private AdmProfileService profileService;
	
	@Inject
	private AdmUserService userService;
	
	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
		log.info("Init " + this.getClass().getName());
		
		AdmProfile role1 = profileService.insert(new AdmProfile(null, "ADMIN", true, false));
		AdmProfile role2 = profileService.insert(new AdmProfile(null, "USER", false, true));
		
		Set<AdmProfile> roles = new HashSet<AdmProfile>();
		roles.add(role1);
		roles.add(role2);
		
		//senha = 123456
		AdmUser user0 = new AdmUser(null, "admin", "$2a$10$nhU38YCtaWpLzTIeG/uAIeGnu7GItrvGsQAQrgsjM9hN19cGp25N6",
				"Henrique", "henrique.souza@gmail.com");
		user0.setCpf(new BigDecimal("02685748474"));
		
		AdmUser user1 = userService.insert(user0);
		
		Set<AdmUser> users = new HashSet<AdmUser>();
		users.add(user1);

		role1.setAdmUsers(users);
		profileService.update(role1);
		
		
		log.info("-------------------------------");
		for (AdmUser item : userService.findAll()) {
			log.info(item.toString());
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
