package br.com.hfs.graph.mutation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmParameterCategoryDTO;
import br.com.hfs.admin.controller.form.AdmParameterCategoryForm;
import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Service
public class AdmParameterCategoryMutationResolver implements GraphQLMutationResolver {
	
	@Autowired
	private AdmParameterCategoryService admParameterCategoryService;

	public AdmParameterCategoryDTO admParameterCategoryInsert(final String description, Long order) {
		AdmParameterCategoryForm form = new AdmParameterCategoryForm(description, order);
		AdmParameterCategory obj = form.convert();
		admParameterCategoryService.insert(obj);
		return new AdmParameterCategoryDTO(obj);
	}
	
	public AdmParameterCategoryDTO admParameterCategoryUpdate(final Long id, final String description, Long order) {
		Optional<AdmParameterCategory> bean = admParameterCategoryService.findById(id);
		if (bean.isPresent()) {
			AdmParameterCategoryForm form = new AdmParameterCategoryForm(description, order);
			AdmParameterCategory parameterCategory = form.update(id, admParameterCategoryService);
			return new AdmParameterCategoryDTO(parameterCategory);
		}
		return null;		
	}

	public boolean admParameterCategoryDelete(final Long id) {
		Optional<AdmParameterCategory> bean = admParameterCategoryService.findById(id);
		if (bean.isPresent()) {
			admParameterCategoryService.deleteById(id);
			return true;
		}
		return false;
	}

}
