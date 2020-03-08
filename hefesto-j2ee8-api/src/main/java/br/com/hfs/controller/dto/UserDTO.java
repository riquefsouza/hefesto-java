package br.com.hfs.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.hfs.model.Role;
import br.com.hfs.model.User;

public class UserDTO {

	private String userName;

	private String password;

	private boolean active;
	
	private List<RoleDTO> roles;
	
	public UserDTO(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.active = user.isActive();
		List<Role> listRoles = new ArrayList<Role>(user.getRoles()); 
		this.roles = RoleDTO.convert(listRoles);
	}
	
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return active;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}
	
	public static List<UserDTO> convert(List<User> users) {
		return users.stream().map(UserDTO::new).collect(Collectors.toList());
	}

}
