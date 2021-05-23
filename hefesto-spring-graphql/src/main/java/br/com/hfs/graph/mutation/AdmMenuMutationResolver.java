package br.com.hfs.graph.mutation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmMenuDTO;
import br.com.hfs.admin.controller.form.AdmMenuForm;
import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Service
public class AdmMenuMutationResolver implements GraphQLMutationResolver {

	@Autowired
	private AdmMenuService admMenuService;

	public AdmMenuDTO admMenuInsert(final String description, Integer order, Long idPage, Long idMenuParent) {
		AdmMenuForm form = new AdmMenuForm(description, order, idPage, idMenuParent);
		AdmMenu obj = form.convert();
		admMenuService.insert(obj);
		return new AdmMenuDTO(obj);
	}

	public AdmMenuDTO admMenuUpdate(final Long id, final String description, Integer order, Long idPage,
			Long idMenuParent) {
		Optional<AdmMenu> bean = admMenuService.findById(id);
		if (bean.isPresent()) {
			AdmMenuForm form = new AdmMenuForm(description, order, idPage, idMenuParent);
			AdmMenu parameter = form.update(id, admMenuService);
			return new AdmMenuDTO(parameter);
		}
		return null;
	}

	public boolean admMenuDelete(final Long id) {
		Optional<AdmMenu> bean = admMenuService.findById(id);
		if (bean.isPresent()) {
			admMenuService.deleteById(id);
			return true;
		}
		return false;
	}

}
