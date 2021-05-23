package br.com.hfs.graph.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmUserDTO;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmUserService;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Service
public class AdmUserQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private AdmUserService admUserService;

	/*
	public AdmUserQueryResolver(AdmUserService admUserService) {
		this.admUserService = admUserService;
	}
	*/

	public List<AdmUserDTO> admUserFindAll() {		
		List<AdmUser> objList = admUserService.findAll();
		return AdmUserDTO.convert(objList);		
	}

	public AdmUserDTO admUserFindById(Long id) {
		Optional<AdmUser> bean = admUserService.findById(id);
		if (bean.isPresent()) {
			return new AdmUserDTO(bean.get());
		}		
		return null;
	}
}
