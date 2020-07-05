package br.com.hfs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.hfs.base.BaseService;
import br.com.hfs.model.AdmParameter;
import br.com.hfs.repository.AdmParameterRepository;

@Service
public class AdmParameterService extends BaseService<AdmParameter, Long, AdmParameterRepository> {

	private static final long serialVersionUID = 1L;

	public AdmParameterService() {
		super(AdmParameter.class);
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
