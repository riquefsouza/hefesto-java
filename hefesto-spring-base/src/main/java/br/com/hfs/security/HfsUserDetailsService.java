package br.com.hfs.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.repository.AdmProfileRepository;
import br.com.hfs.admin.repository.AdmUserRepository;
import br.com.hfs.base.security.SystemService;

@Service
public class HfsUserDetailsService implements UserDetailsService {

	private static Logger log = LogManager.getLogger(HfsUserDetailsService.class);
	
	@Autowired
	private AdmUserRepository userRepository;

	@Autowired
	private AdmProfileRepository profileRepository;

	@Autowired
	private SystemService systemService; 
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<AdmUser> user = userRepository.findByLogin(userName);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		
		Set<AdmProfile> roles = new HashSet<AdmProfile>(profileRepository.findProfilesByUser(user.get().getId()));
		
		HfsUserDetails userDetails = new HfsUserDetails(user.get(), roles);
		
		try {
			if (systemService.authenticate(user.get())) {
				userDetails.setAuthenticatedUser(systemService.getAuthenticatedUser());
			}
		} catch (Exception e) {
			log.error("Error user authentication", e);
		}
		
		return userDetails;
	}


	
	
}
