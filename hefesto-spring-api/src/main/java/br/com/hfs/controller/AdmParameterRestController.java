package br.com.hfs.controller;

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

import br.com.hfs.controller.dto.AdmParameterDTO;
import br.com.hfs.controller.form.AdmParameterForm;
import br.com.hfs.model.AdmParameter;
import br.com.hfs.model.AdmParameterCategory;
import br.com.hfs.service.AdmParameterCategoryService;
import br.com.hfs.service.AdmParameterService;

@RestController
@RequestMapping("/api/v1/admParameter")
public class AdmParameterRestController {

	@Autowired
	private AdmParameterService admParameterService;

	@Autowired
	private AdmParameterCategoryService admParameterCategoryService;
	
	@GetMapping("/paged")
	@Cacheable(value = "admParameterControllerList")
	public Page<AdmParameterDTO> listPaged(@RequestParam(required = false) String description, 
			@PageableDefault(page = 0, size = 20, direction = Direction.ASC, sort = "id") Pageable pagination) {
		//@RequestParam int page, @RequestParam int size, @RequestParam String fieldToSort) {
	
		//Pageable pagination = PageRequest.of(page, size, Direction.ASC, fieldToSort);
		
		if (description == null) {
			Page<AdmParameter> parameters = admParameterService.findAll(pagination);
			return AdmParameterDTO.convert(parameters);
		} else {
			Page<AdmParameter> parameters = admParameterService.findByDescriptionLike(description + "%", pagination);
			return AdmParameterDTO.convert(parameters);
		}
	}
	
	@GetMapping()
	@Cacheable(value = "admParameterControllerList")
	public List<AdmParameterDTO> listAll(@RequestParam(required = false) String description) {		
		if (description == null) {
			List<AdmParameter> objList = admParameterService.findAll();
			return AdmParameterDTO.convert(objList);
		} else {
			List<AdmParameter> objList = admParameterService.findByDescriptionLike(description + "%");
			return AdmParameterDTO.convert(objList);
		}
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "admParameterControllerList", allEntries = true)
	public ResponseEntity<AdmParameterDTO> save(@RequestBody @Valid AdmParameterForm form, UriComponentsBuilder uriBuilder) {
		AdmParameter obj = form.convert();
		obj = admParameterService.insert(obj);
		//AdmParameter obj2 = admParameterService.findById(obj.getId()).get();
		
		Optional<AdmParameterCategory> opt = admParameterCategoryService.findById(obj.getIdParameterCategory());
		if (opt.isPresent()) {
			obj.setParameterCategory(opt.get());
		}
		
		
		URI uri = uriBuilder.path("/api/v1/admParameter/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(new AdmParameterDTO(obj));
	}

	@GetMapping("{id}")
	public ResponseEntity<AdmParameterDTO> get(@PathVariable Long id) {
		Optional<AdmParameter> bean = admParameterService.findById(id);
		if (bean.isPresent()) {
			return ResponseEntity.ok(new AdmParameterDTO(bean.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	@Transactional
	@CacheEvict(value = "admParameterControllerList", allEntries = true)
	public ResponseEntity<AdmParameterDTO> update(@PathVariable Long id, @RequestBody @Valid AdmParameterForm form){
		Optional<AdmParameter> bean = admParameterService.findById(id);
		if (bean.isPresent()) {
			AdmParameter parameter = form.update(id, admParameterService);
			return ResponseEntity.ok(new AdmParameterDTO(parameter));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	@CacheEvict(value = "admParameterControllerList", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<AdmParameter> bean = admParameterService.findById(id);
		if (bean.isPresent()) {
			admParameterService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
