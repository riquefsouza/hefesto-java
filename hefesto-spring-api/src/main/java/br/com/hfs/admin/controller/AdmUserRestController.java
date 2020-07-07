package br.com.hfs.admin.controller;

import java.net.URI;
import java.util.List;
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

import br.com.hfs.admin.controller.dto.AdmUserDTO;
import br.com.hfs.admin.controller.form.AdmUserForm;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;

@RestController
@RequestMapping("/api/v1/admUser")
public class AdmUserRestController {

	@Autowired
	private AdmUserService admUserService;

	@GetMapping("/paged")
	@Cacheable(value = "admUserControllerList")
	public Page<AdmUserDTO> listPaged(@RequestParam(required = false) String name, 
			@PageableDefault(page = 0, size = 20, direction = Direction.ASC, sort = "id") Pageable pagination) {
		//@RequestParam int page, @RequestParam int size, @RequestParam String fieldToSort) {
	
		//Pageable pagination = PageRequest.of(page, size, Direction.ASC, fieldToSort);
		
		if (name == null) {
			Page<AdmUser> users = admUserService.findAll(pagination);
			return AdmUserDTO.convert(users);
		} else {
			Page<AdmUser> users = admUserService.findByNameLike(name + "%", pagination);
			return AdmUserDTO.convert(users);
		}
	}
	
	@GetMapping()
	@Cacheable(value = "admUserControllerList")
	public List<AdmUserDTO> listAll(@RequestParam(required = false) String name) {		
		if (name == null) {
			List<AdmUser> objList = admUserService.findAll();
			return AdmUserDTO.convert(objList);
		} else {
			List<AdmUser> objList = admUserService.findByNameLike(name + "%");
			return AdmUserDTO.convert(objList);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "admUserControllerList", allEntries = true)
	public ResponseEntity<AdmUserDTO> save(@RequestBody @Valid AdmUserForm form, UriComponentsBuilder uriBuilder) {
		AdmUser obj = form.convert();
		admUserService.insert(obj);
		
		URI uri = uriBuilder.path("/api/v1/admUser/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(new AdmUserDTO(obj));
	}

	@GetMapping("{id}")
	public ResponseEntity<AdmUserDTO> get(@PathVariable Long id) {
		Optional<AdmUser> bean = admUserService.findById(id);
		if (bean.isPresent()) {
			return ResponseEntity.ok(new AdmUserDTO(bean.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	@Transactional
	@CacheEvict(value = "admUserControllerList", allEntries = true)
	public ResponseEntity<AdmUserDTO> update(@PathVariable Long id, @RequestBody @Valid AdmUserForm form){
		Optional<AdmUser> bean = admUserService.findById(id);
		if (bean.isPresent()) {
			AdmUser user = form.update(id, admUserService);
			return ResponseEntity.ok(new AdmUserDTO(user));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	@CacheEvict(value = "admUserControllerList", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<AdmUser> bean = admUserService.findById(id);
		if (bean.isPresent()) {
			admUserService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
