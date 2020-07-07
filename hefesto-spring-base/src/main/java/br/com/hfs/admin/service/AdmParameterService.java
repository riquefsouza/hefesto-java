package br.com.hfs.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.repository.AdmParameterRepository;
import br.com.hfs.base.BaseService;

@Service
public class AdmParameterService extends BaseService<AdmParameter, Long, AdmParameterRepository> {

	private static final long serialVersionUID = 1L;

	public AdmParameterService() {
		super(AdmParameter.class);
	}
	
	public Page<AdmParameter> findByDescriptionLike(String description, Pageable pagination){
		return repository.findByDescriptionLike(description, pagination);
	}
	
	public List<AdmParameter> findByDescriptionLike(String description){
		return repository.findByDescriptionLike(description);
	}
	
	public List<AdmParameter> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PARAMETER", "PAR_SEQ", pageNumber, pageSize);
	}

	public List<AdmParameter> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PARAMETER", "PAR_SEQ", startInterval, endInterval);
	}
	
	public String getValueByCode(String code){
		try {
			return repository.getValueByCode(code);
		} catch (Exception e) {
			return null;
		}
	}
}
