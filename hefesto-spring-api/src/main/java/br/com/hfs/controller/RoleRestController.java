package br.com.hfs.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.hfs.controller.dto.RoleDTO;
import br.com.hfs.controller.form.RoleForm;
import br.com.hfs.model.Role;
import br.com.hfs.repository.RoleRepository;

@RestController
@RequestMapping("/api/v1/role")
public class RoleRestController {

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping
	@Cacheable(value = "roleControllerList")
	public Page<RoleDTO> list(@RequestParam(required = false) String role,
		@PageableDefault(page = 0, size = 20, direction = Direction.ASC, sort = "id") Pageable pagination) {
			//@RequestParam int page, @RequestParam int size, @RequestParam String fieldToSort) {
		
		//Pageable pagination = PageRequest.of(page, size, Direction.ASC, fieldToSort);
		
		if (role == null) {
			Page<Role> roles = roleRepository.findAll(pagination);
			return RoleDTO.convert(roles);
		} else {
			Page<Role> roles = roleRepository.findByRole(role, pagination);
			return RoleDTO.convert(roles);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "roleControllerList", allEntries = true)
	public ResponseEntity<RoleDTO> save(@RequestBody @Valid RoleForm form, UriComponentsBuilder uriBuilder) {
		Role role = form.convert();
		roleRepository.save(role);
		
		URI uri = uriBuilder.path("/api/v1/role/{id}").buildAndExpand(role.getId()).toUri();
		return ResponseEntity.created(uri).body(new RoleDTO(role));
	}

	@GetMapping("{id}")
	public ResponseEntity<RoleDTO> get(@PathVariable Long id) {
		Optional<Role> bean = roleRepository.findById(id);
		if (bean.isPresent()) {
			return ResponseEntity.ok(new RoleDTO(bean.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	@Transactional
	@CacheEvict(value = "roleControllerList", allEntries = true)
	public ResponseEntity<RoleDTO> update(@PathVariable Long id, @RequestBody @Valid RoleForm form){
		Optional<Role> bean = roleRepository.findById(id);
		if (bean.isPresent()) {
			Role role = form.update(id, roleRepository);
			return ResponseEntity.ok(new RoleDTO(role));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	@CacheEvict(value = "roleControllerList", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Role> bean = roleRepository.findById(id);
		if (bean.isPresent()) {
			roleRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
