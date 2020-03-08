package br.com.hfs.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	List<Role> findByRole(String role);
	
	Page<Role> findByRole(String role, Pageable pagination);
}
