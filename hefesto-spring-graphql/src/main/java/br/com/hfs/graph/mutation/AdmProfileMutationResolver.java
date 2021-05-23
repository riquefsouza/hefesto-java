package br.com.hfs.graph.mutation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmProfileDTO;
import br.com.hfs.admin.controller.form.AdmProfileForm;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.service.AdmProfileService;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Service
public class AdmProfileMutationResolver implements GraphQLMutationResolver {
	
	@Autowired
	private AdmProfileService admProfileService;

	public AdmProfileDTO admProfileInsert(final String description, final Boolean administrator, final Boolean general, 
			final List<Long> idAdmPages, final List<Long> idAdmUsers) {
		AdmProfileForm form = new AdmProfileForm(description, administrator, general, idAdmPages, idAdmUsers);
		AdmProfile obj = form.convert();
		admProfileService.insert(obj);
		return new AdmProfileDTO(obj);
	}
	
	public AdmProfileDTO admProfileUpdate(final Long id, final String description, 
			final Boolean administrator, final Boolean general, 
			final List<Long> idAdmPages, final List<Long> idAdmUsers) {
		Optional<AdmProfile> bean = admProfileService.findById(id);
		if (bean.isPresent()) {
			AdmProfileForm form = new AdmProfileForm(description, administrator, general, idAdmPages, idAdmUsers);
			AdmProfile parameter = form.update(id, admProfileService);
			return new AdmProfileDTO(parameter);
		}
		return null;		
	}

	public boolean admProfileDelete(final Long id) {
		Optional<AdmProfile> bean = admProfileService.findById(id);
		if (bean.isPresent()) {
			admProfileService.deleteById(id);
			return true;
		}
		return false;
	}

}
