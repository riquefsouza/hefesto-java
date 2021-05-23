package br.com.hfs.graph.mutation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmPageDTO;
import br.com.hfs.admin.controller.form.AdmPageForm;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmPageService;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Service
public class AdmPageMutationResolver implements GraphQLMutationResolver {
	
	@Autowired
	private AdmPageService admPageService;

	public AdmPageDTO admPageInsert(final String description, final String url) {
		AdmPageForm form = new AdmPageForm(description, url);
		AdmPage obj = form.convert();
		admPageService.insert(obj);
		return new AdmPageDTO(obj);
	}
	
	public AdmPageDTO admPageUpdate(final Long id, final String description, final String url) {
		Optional<AdmPage> bean = admPageService.findById(id);
		if (bean.isPresent()) {
			AdmPageForm form = new AdmPageForm(description, url);
			AdmPage parameter = form.update(id, admPageService);
			return new AdmPageDTO(parameter);
		}
		return null;		
	}

	public boolean admPageDelete(final Long id) {
		Optional<AdmPage> bean = admPageService.findById(id);
		if (bean.isPresent()) {
			admPageService.deleteById(id);
			return true;
		}
		return false;
	}

}
