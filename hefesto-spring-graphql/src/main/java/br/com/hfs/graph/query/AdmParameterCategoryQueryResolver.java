package br.com.hfs.graph.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmParameterCategoryDTO;
import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Service
public class AdmParameterCategoryQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private AdmParameterCategoryService admParameterCategoryService;

	public Page<AdmParameterCategoryDTO> admParameterCategoryListPaged(final Integer page, final Integer size) {	
		Pageable pagination = PageRequest.of(page, size, Direction.ASC, "id");
		Page<AdmParameterCategory> obj = admParameterCategoryService.findAll(pagination);
		return AdmParameterCategoryDTO.convert(obj);
	}

	public List<AdmParameterCategoryDTO> admParameterCategoryListAll() {
		List<AdmParameterCategory> objList = admParameterCategoryService.findAll();
		return AdmParameterCategoryDTO.convert(objList);		
	}

	public AdmParameterCategoryDTO admParameterCategoryFindById(final Long id) {
		Optional<AdmParameterCategory> bean = admParameterCategoryService.findById(id);
		if (bean.isPresent()) {
			return new AdmParameterCategoryDTO(bean.get());
		}
		return null;
	}

}
