package br.com.hfs.graph.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.controller.dto.AdmPageDTO;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmPageService;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Service
public class AdmPageQueryResolver implements GraphQLQueryResolver {
	
	@Autowired
	private AdmPageService admPageService;

	public Page<AdmPageDTO> admPageListPaged(final Integer page, final Integer size) {	
		Pageable pagination = PageRequest.of(page, size, Direction.ASC, "id");
		Page<AdmPage> obj = admPageService.findAll(pagination);
		return AdmPageDTO.convert(obj);
	}

	public List<AdmPageDTO> admPageListAll() {
		List<AdmPage> objList = admPageService.findAll();
		return AdmPageDTO.convert(objList);		
	}

	public AdmPageDTO admPageFindById(final Long id) {
		Optional<AdmPage> bean = admPageService.findById(id);
		if (bean.isPresent()) {
			return new AdmPageDTO(bean.get());
		}
		return null;
	}

}
