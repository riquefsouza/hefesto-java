package br.com.hfs.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.vo.AuthenticatedUserVO;

public class HfsUserDetails implements UserDetails {

	private static final long serialVersionUID = -2542040140065565220L;

	private Long id;
	private String userName;
	private String email;
	private String password;
	private boolean active;
	private Set<AdmProfile> roles;
	private AuthenticatedUserVO authenticatedUser;
	
	public HfsUserDetails() {
		super();
	}

	public HfsUserDetails(AdmUser user, Set<AdmProfile> roles) {
		this.id = user.getId();
		this.userName = user.getLogin();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.active = user.getActive();
		this.roles = roles; //user.getRoles();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getDescription()))
                .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPayload() {
		return "{'id': '"+ this.id+ "','name': '" + this.userName +"', 'email': '" + this.email + "'}";
	}

	public Map<String, Object> getClaims() {
		List<String> idProfiles = new ArrayList<String>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("name", this.userName);
		map.put("email", this.email);
		this.roles.forEach(profile -> {
			idProfiles.add(profile.getId().toString());			
		});
		map.put("idProfiles", String.join(",", idProfiles));
		return map;
	}

	public AuthenticatedUserVO getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(AuthenticatedUserVO authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

}
