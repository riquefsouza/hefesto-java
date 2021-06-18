package br.com.hfs.admin.repository;

import java.util.List;

import jakarta.persistence.TypedQuery;

import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.base.BaseRepository;

public class AdmParameterRepository extends BaseRepository<AdmParameter, Long> {

	private static final long serialVersionUID = 1L;

	public AdmParameterRepository() {
		super(AdmParameter.class);
	}
	
	public List<AdmParameter> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PARAMETER", "PAR_SEQ", pageNumber, pageSize);
	}

	public List<AdmParameter> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PARAMETER", "PAR_SEQ", startInterval, endInterval);
	}

	public String getValueByCode(String code) {
		TypedQuery<String> query = em.createNamedQuery("AdmParameter.getValueByCode", String.class);
		query.setParameter(1, code);
		return query.getSingleResult();
	}
}
