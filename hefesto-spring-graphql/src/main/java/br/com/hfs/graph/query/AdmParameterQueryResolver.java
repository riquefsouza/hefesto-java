package br.com.hfs.graph.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmParameterDTO;
import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.service.AdmParameterService;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Service
public class AdmParameterQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private AdmParameterService admParameterService;

	public Page<AdmParameterDTO> admParameterListPaged(final Integer page, final Integer size) {	
		Pageable pagination = PageRequest.of(page, size, Direction.ASC, "id");
		Page<AdmParameter> obj = admParameterService.findAll(pagination);
		return AdmParameterDTO.convert(obj);
	}

	public List<AdmParameterDTO> admParameterListAll() {
		List<AdmParameter> objList = admParameterService.findAll();
		return AdmParameterDTO.convert(objList);		
	}

	public AdmParameterDTO admParameterFindById(final Long id) {
		Optional<AdmParameter> bean = admParameterService.findById(id);
		if (bean.isPresent()) {
			return new AdmParameterDTO(bean.get());
		}
		return null;
	}

}
