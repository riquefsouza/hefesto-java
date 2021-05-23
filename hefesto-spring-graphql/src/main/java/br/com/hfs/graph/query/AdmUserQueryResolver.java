package br.com.hfs.graph.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmUserDTO;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Service
public class AdmUserQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private AdmUserService admUserService;

	public Page<AdmUserDTO> admUserListPaged(final Integer page, final Integer size) {	
		Pageable pagination = PageRequest.of(page, size, Direction.ASC, "id");
		Page<AdmUser> obj = admUserService.findAll(pagination);
		return AdmUserDTO.convert(obj);
	}

	public List<AdmUserDTO> admUserListAll() {		
		List<AdmUser> objList = admUserService.findAll();
		return AdmUserDTO.convert(objList);		
	}

	public AdmUserDTO admUserFindById(final Long id) {
		Optional<AdmUser> bean = admUserService.findById(id);
		if (bean.isPresent()) {
			return new AdmUserDTO(bean.get());
		}		
		return null;
	}
}
