package br.com.hfs.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.repository.AdmProfileRepository;
import br.com.hfs.admin.repository.AdmUserRepository;

@Service
public class HfsUserDetailsService implements UserDetailsService {

	@Autowired
	private AdmUserRepository userRepository;

	@Autowired
	private AdmProfileRepository profileRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<AdmUser> user = userRepository.findByLogin(userName);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		
		Set<AdmProfile> roles = new HashSet<AdmProfile>(profileRepository.findProfilesByUser(user.get().getId()));
		
		HfsUserDetails userDetails = new HfsUserDetails(user.get(), roles);
		return userDetails;
		//return user.map(HfsUserDetails::new).get();
	}

}
