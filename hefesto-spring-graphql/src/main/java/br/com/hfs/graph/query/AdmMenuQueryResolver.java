package br.com.hfs.graph.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmMenuDTO;
import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Service
public class AdmMenuQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private AdmMenuService admMenuService;

	public Page<AdmMenuDTO> admMenuListPaged(final Integer page, final Integer size) {	
		Pageable pagination = PageRequest.of(page, size, Direction.ASC, "id");
		Page<AdmMenu> obj = admMenuService.findAll(pagination);
		return AdmMenuDTO.convert(obj);
	}

	public List<AdmMenuDTO> admMenuListAll() {		
		List<AdmMenu> objList = admMenuService.findAll();
		return AdmMenuDTO.convert(objList);		
	}

	public AdmMenuDTO admMenuFindById(final Long id) {
		Optional<AdmMenu> bean = admMenuService.findById(id);
		if (bean.isPresent()) {
			return new AdmMenuDTO(bean.get());
		}		
		return null;
	}
}
