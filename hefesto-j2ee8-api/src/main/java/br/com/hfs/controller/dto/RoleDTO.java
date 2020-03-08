package br.com.hfs.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.hfs.model.Role;

public class RoleDTO {

	private String role;

	public RoleDTO(Role role) {
		this.role = role.getRole();
	}
	
	public String getRole() {
		return role;
	}
	
	public static List<RoleDTO> convert(List<Role> roles) {
		return roles.stream().map(RoleDTO::new).collect(Collectors.toList());
	}

}
