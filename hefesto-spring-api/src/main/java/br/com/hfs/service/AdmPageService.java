package br.com.hfs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.hfs.base.BaseService;
import br.com.hfs.model.AdmPage;
import br.com.hfs.repository.AdmPageRepository;

@Service
public class AdmPageService extends BaseService<AdmPage, Long, AdmPageRepository> {

	private static final long serialVersionUID = 1L;

	public AdmPageService() {
		super(AdmPage.class);
	}
	
	public List<AdmPage> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PAGE", "PAG_SEQ", pageNumber, pageSize);
	}

	public List<AdmPage> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PAGE", "PAG_SEQ", startInterval, endInterval);
	}
	
}
