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

import br.com.hfs.admin.controller.dto.AdmParameterCategoryDTO;
import br.com.hfs.admin.controller.form.AdmParameterCategoryForm;
import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;

@RestController
@RequestMapping("/api/v1/admParameterCategory")
public class AdmParameterCategoryRestController {

	@Autowired
	private AdmParameterCategoryService admParameterCategoryService;

	@GetMapping("/paged")
	@Cacheable(value = "admParameterCategoryControllerList")
	public Page<AdmParameterCategoryDTO> listPaged(@RequestParam(required = false) String description, 
			@PageableDefault(page = 0, size = 20, direction = Direction.ASC, sort = "id") Pageable pagination) {
		//@RequestParam int page, @RequestParam int size, @RequestParam String fieldToSort) {
	
		//Pageable pagination = PageRequest.of(page, size, Direction.ASC, fieldToSort);
		
		if (description == null) {
			Page<AdmParameterCategory> obj = admParameterCategoryService.findAll(pagination);
			return AdmParameterCategoryDTO.convert(obj);
		} else {
			Page<AdmParameterCategory> obj = admParameterCategoryService.findByDescriptionLike(description + "%", pagination);
			return AdmParameterCategoryDTO.convert(obj);
		}
	}
	
	@GetMapping()
	@Cacheable(value = "admParameterCategoryControllerList")
	public List<AdmParameterCategoryDTO> listAll(@RequestParam(required = false) String description) {		
		if (description == null) {
			List<AdmParameterCategory> objList = admParameterCategoryService.findAll();
			return AdmParameterCategoryDTO.convert(objList);
		} else {
			List<AdmParameterCategory> objList = admParameterCategoryService.findByDescriptionLike(description + "%");
			return AdmParameterCategoryDTO.convert(objList);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "admParameterCategoryControllerList", allEntries = true)
	public ResponseEntity<AdmParameterCategoryDTO> save(@RequestBody @Valid AdmParameterCategoryForm form, UriComponentsBuilder uriBuilder) {
		AdmParameterCategory obj = form.convert();
		admParameterCategoryService.insert(obj);
		
		URI uri = uriBuilder.path("/api/v1/admParameterCategory/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(new AdmParameterCategoryDTO(obj));
	}

	@GetMapping("{id}")
	public ResponseEntity<AdmParameterCategoryDTO> get(@PathVariable Long id) {
		Optional<AdmParameterCategory> bean = admParameterCategoryService.findById(id);
		if (bean.isPresent()) {
			return ResponseEntity.ok(new AdmParameterCategoryDTO(bean.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	@Transactional
	@CacheEvict(value = "admParameterCategoryControllerList", allEntries = true)
	public ResponseEntity<AdmParameterCategoryDTO> update(@PathVariable Long id, @RequestBody @Valid AdmParameterCategoryForm form){
		Optional<AdmParameterCategory> bean = admParameterCategoryService.findById(id);
		if (bean.isPresent()) {
			AdmParameterCategory parameterCategory = form.update(id, admParameterCategoryService);
			return ResponseEntity.ok(new AdmParameterCategoryDTO(parameterCategory));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	@CacheEvict(value = "admParameterCategoryControllerList", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<AdmParameterCategory> bean = admParameterCategoryService.findById(id);
		if (bean.isPresent()) {
			admParameterCategoryService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
