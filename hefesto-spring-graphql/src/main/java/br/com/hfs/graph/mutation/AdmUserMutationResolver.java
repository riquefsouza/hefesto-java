package br.com.hfs.graph.mutation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmUserDTO;
import br.com.hfs.admin.controller.form.AdmUserForm;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Service
public class AdmUserMutationResolver implements GraphQLMutationResolver {
	
	@Autowired
	private AdmUserService admUserService;

	public AdmUserDTO admUserInsert(final String login, final String password, String name, String email,
			final Boolean active, final List<Long> admIdProfiles) {

		AdmUserForm form = new AdmUserForm(login, password, name, email, active, admIdProfiles);
		AdmUser obj = form.convert();
		admUserService.insert(obj);
		return new AdmUserDTO(obj);
	}

	public AdmUserDTO admUserUpdate(final Long id, final String login, final String password, String name, String email,
			final Boolean active, final List<Long> admIdProfiles) {
		Optional<AdmUser> bean = admUserService.findById(id);
		if (bean.isPresent()) {
			AdmUserForm form = new AdmUserForm(login, password, name, email, active, admIdProfiles);
			AdmUser parameterCategory = form.update(id, admUserService);
			return new AdmUserDTO(parameterCategory);
		}
		return null;		
	}
	
	public boolean admUserDelete(final Long id) {
		Optional<AdmUser> bean = admUserService.findById(id);
		if (bean.isPresent()) {
			admUserService.deleteById(id);
			return true;
		}
		return false;
	}
	
}
