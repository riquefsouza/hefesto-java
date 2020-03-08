package br.com.hfs.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import br.com.hfs.dao.RoleDAO;
import br.com.hfs.model.Role;

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

	public Role update(Role bean, RoleDAO dao) {
		Optional<Role> role = dao.findById(bean);
		if (role.isPresent()) {
			role.get().setRole(this.role);
			return role.get();			
		}
		return null;
	}
}
