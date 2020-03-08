package br.com.hfs.controller.form;

import javax.validation.constraints.NotBlank;

import br.com.hfs.model.Role;
import br.com.hfs.repository.RoleRepository;

public class RoleForm {

	@NotBlank
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Role convert() {
		return new Role(role);
	}

	public Role update(Long id, RoleRepository roleRepository) {
		Role role = roleRepository.getOne(id);
		role.setRole(this.role);
		return role;
	}
}
