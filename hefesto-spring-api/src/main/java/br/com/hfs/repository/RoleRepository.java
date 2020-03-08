package br.com.hfs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Page<Role> findByRole(String role, Pageable pagination);

}
