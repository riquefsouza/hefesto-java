package br.com.hfs.graph.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmParameterCategoryDTO;
import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Service
public class AdmParameterCategoryQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private AdmParameterCategoryService admParameterCategoryService;

	/*
	public AdmParameterCategoryQueryResolver(AdmParameterCategoryService admParameterCategoryService) {
		this.admParameterCategoryService = admParameterCategoryService;
	}
	*/

	public List<AdmParameterCategoryDTO> admParameterCategoryFindAll() {
		List<AdmParameterCategory> objList = admParameterCategoryService.findAll();
		return AdmParameterCategoryDTO.convert(objList);		
	}

	public AdmParameterCategoryDTO admParameterCategoryFindById(Long id) {
		Optional<AdmParameterCategory> bean = admParameterCategoryService.findById(id);
		if (bean.isPresent()) {
			return new AdmParameterCategoryDTO(bean.get());
		}
		return null;
	}

}
