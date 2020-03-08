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

import br.com.hfs.controller.dto.UserDTO;
import br.com.hfs.controller.form.UserForm;
import br.com.hfs.model.User;
import br.com.hfs.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	@Cacheable(value = "userControllerList")
	public Page<UserDTO> list(@RequestParam(required = false) String user, 
			@PageableDefault(page = 0, size = 20, direction = Direction.ASC, sort = "id") Pageable pagination) {
		//@RequestParam int page, @RequestParam int size, @RequestParam String fieldToSort) {
	
		//Pageable pagination = PageRequest.of(page, size, Direction.ASC, fieldToSort);
		
		if (user == null) {
			Page<User> users = userRepository.findAll(pagination);
			return UserDTO.convert(users);
		} else {
			Page<User> users = userRepository.findByUserNameLike(user + "%", pagination);
			return UserDTO.convert(users);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "userControllerList", allEntries = true)
	public ResponseEntity<UserDTO> save(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {
		User user = form.convert();
		userRepository.save(user);
		
		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDTO(user));
	}

	@GetMapping("{id}")
	public ResponseEntity<UserDTO> get(@PathVariable Long id) {
		Optional<User> bean = userRepository.findById(id);
		if (bean.isPresent()) {
			return ResponseEntity.ok(new UserDTO(bean.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	@Transactional
	@CacheEvict(value = "userControllerList", allEntries = true)
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody @Valid UserForm form){
		Optional<User> bean = userRepository.findById(id);
		if (bean.isPresent()) {
			User user = form.update(id, userRepository);
			return ResponseEntity.ok(new UserDTO(user));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	@CacheEvict(value = "userControllerList", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<User> bean = userRepository.findById(id);
		if (bean.isPresent()) {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
