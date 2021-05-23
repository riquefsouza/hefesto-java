package br.com.hfs.graph.mutation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmParameterDTO;
import br.com.hfs.admin.controller.form.AdmParameterForm;
import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.service.AdmParameterService;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Service
public class AdmParameterMutationResolver implements GraphQLMutationResolver {
	
	@Autowired
	private AdmParameterService admParameterService;

	public AdmParameterDTO admParameterInsert(final String code, final String description, 
			String value, final Long idAdmParameterCategory) {
		AdmParameterForm form = new AdmParameterForm(code, description, value, idAdmParameterCategory);
		AdmParameter obj = form.convert();
		admParameterService.insert(obj);
		return new AdmParameterDTO(obj);
	}
	
	public AdmParameterDTO admParameterUpdate(final Long id, final String code, final String description, 
			String value, final Long idAdmParameterCategory) {
		Optional<AdmParameter> bean = admParameterService.findById(id);
		if (bean.isPresent()) {
			AdmParameterForm form = new AdmParameterForm(code, description, value, idAdmParameterCategory);
			AdmParameter parameter = form.update(id, admParameterService);
			return new AdmParameterDTO(parameter);
		}
		return null;		
	}

	public boolean admParameterDelete(final Long id) {
		Optional<AdmParameter> bean = admParameterService.findById(id);
		if (bean.isPresent()) {
			admParameterService.deleteById(id);
			return true;
		}
		return false;
	}

}
