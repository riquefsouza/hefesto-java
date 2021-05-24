package br.com.hfs.graph.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmProfileDTO;
import br.com.hfs.admin.controller.dto.MenuItemDTO;
import br.com.hfs.admin.model.AdmProfile;
import br.com.hfs.admin.service.AdmProfileService;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Service
public class AdmProfileQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private AdmProfileService admProfileService;

	public Page<AdmProfileDTO> admProfileListPaged(final Integer page, final Integer size) {	
		Pageable pagination = PageRequest.of(page, size, Direction.ASC, "id");
		Page<AdmProfile> obj = admProfileService.findAll(pagination);
		return AdmProfileDTO.convert(obj);
	}

	public List<AdmProfileDTO> admProfileListAll() {		
		List<AdmProfile> objList = admProfileService.findAll();
		return AdmProfileDTO.convert(objList);		
	}

	public AdmProfileDTO admProfileFindById(final Long id) {
		Optional<AdmProfile> bean = admProfileService.findById(id);
		if (bean.isPresent()) {
			return new AdmProfileDTO(bean.get());
		}		
		return null;
	}
	
	public List<MenuItemDTO> mountMenu(final List<Long> listaIdProfile){
		return admProfileService.mountMenuItem(listaIdProfile);
	}
	
	public List<AdmProfileDTO> admProfileFindProfilesByPage(final Long pageId) {		
		List<AdmProfile> objList = admProfileService.findProfilesByPage(pageId);
		return AdmProfileDTO.convert(objList);
	}
	
	public List<AdmProfileDTO> admProfileFindProfilesByUser(final Long userId) {		
		List<AdmProfile> objList = admProfileService.findProfilesByUser(userId);
		return AdmProfileDTO.convert(objList);
	}
	
}
