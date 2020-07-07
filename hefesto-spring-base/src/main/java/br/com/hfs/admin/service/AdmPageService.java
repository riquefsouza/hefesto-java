package br.com.hfs.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.repository.AdmPageRepository;
import br.com.hfs.base.BaseService;

@Service
public class AdmPageService extends BaseService<AdmPage, Long, AdmPageRepository> {

	private static final long serialVersionUID = 1L;

	public AdmPageService() {
		super(AdmPage.class);
	}
	
	public Page<AdmPage> findByDescriptionLike(String description, Pageable pagination){
		return repository.findByDescriptionLike(description, pagination);
	}

	public List<AdmPage> findByDescriptionLike(String description){
		return repository.findByDescriptionLike(description);
	}

	public List<AdmPage> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PAGE", "PAG_SEQ", pageNumber, pageSize);
	}

	public List<AdmPage> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PAGE", "PAG_SEQ", startInterval, endInterval);
	}
	
}
