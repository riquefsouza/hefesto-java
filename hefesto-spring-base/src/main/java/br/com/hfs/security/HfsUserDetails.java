package br.com.hfs.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.hfs.model.AdmProfile;
import br.com.hfs.model.AdmUser;

public class HfsUserDetails implements UserDetails {

	private static final long serialVersionUID = -2542040140065565220L;

	private Long id;
	private String userName;
	private String password;
	private boolean active;
	private Set<AdmProfile> roles;
	
	public HfsUserDetails() {
		super();
	}

	public HfsUserDetails(AdmUser user, Set<AdmProfile> roles) {
		this.id = user.getId();
		this.userName = user.getLogin();
		this.password = user.getPassword();
		this.active = true; //user.isActive();
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

}
