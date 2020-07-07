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

import br.com.hfs.admin.controller.dto.AdmMenuDTO;
import br.com.hfs.admin.controller.form.AdmMenuForm;
import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;

@RestController
@RequestMapping("/api/v1/admMenu")
public class AdmMenuRestController {

	@Autowired
	private AdmMenuService admMenuService;

	@GetMapping("/paged")
	@Cacheable(value = "admMenuControllerList")
	public Page<AdmMenuDTO> listPaged(@RequestParam(required = false) String description, 
			@PageableDefault(page = 0, size = 20, direction = Direction.ASC, sort = "id") Pageable pagination) {
		//@RequestParam int page, @RequestParam int size, @RequestParam String fieldToSort) {
	
		//Pageable pagination = PageRequest.of(page, size, Direction.ASC, fieldToSort);
		
		if (description == null) {
			Page<AdmMenu> objList = admMenuService.findAll(pagination);
			return AdmMenuDTO.convert(objList);
		} else {
			Page<AdmMenu> objList = admMenuService.findByDescriptionLike(description + "%", pagination);
			return AdmMenuDTO.convert(objList);
		}
	}
	
	@GetMapping()
	@Cacheable(value = "admMenuControllerList")
	public List<AdmMenuDTO> listAll(@RequestParam(required = false) String description) {		
		if (description == null) {
			List<AdmMenu> objList = admMenuService.findAll();
			return AdmMenuDTO.convert(objList);
		} else {
			List<AdmMenu> objList = admMenuService.findByDescriptionLike(description + "%");
			return AdmMenuDTO.convert(objList);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "admMenuControllerList", allEntries = true)
	public ResponseEntity<AdmMenuDTO> save(@RequestBody @Valid AdmMenuForm form, UriComponentsBuilder uriBuilder) {
		AdmMenu obj = form.convert();
		admMenuService.insert(obj);
		
		
		URI uri = uriBuilder.path("/api/v1/admMenu/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(new AdmMenuDTO(obj));
	}

	@GetMapping("{id}")
	public ResponseEntity<AdmMenuDTO> get(@PathVariable Long id) {
		Optional<AdmMenu> bean = admMenuService.findById(id);
		if (bean.isPresent()) {
			return ResponseEntity.ok(new AdmMenuDTO(bean.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	@Transactional
	@CacheEvict(value = "admMenuControllerList", allEntries = true)
	public ResponseEntity<AdmMenuDTO> update(@PathVariable Long id, @RequestBody @Valid AdmMenuForm form){
		Optional<AdmMenu> bean = admMenuService.findById(id);
		if (bean.isPresent()) {
			AdmMenu menu = form.update(id, admMenuService);
			return ResponseEntity.ok(new AdmMenuDTO(menu));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	@CacheEvict(value = "admMenuControllerList", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<AdmMenu> bean = admMenuService.findById(id);
		if (bean.isPresent()) {
			admMenuService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
