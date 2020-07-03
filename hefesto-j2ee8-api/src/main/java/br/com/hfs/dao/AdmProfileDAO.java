package br.com.hfs.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseDAO;
import br.com.hfs.model.AdmProfile;

@Stateless
public class AdmProfileDAO extends BaseDAO<AdmProfile, Long> {

	private static final long serialVersionUID = 1L;

	public AdmProfileDAO() {
		super(AdmProfile.class);
	}
	
	public List<AdmProfile> findPaginated(int pageNumber, int pageSize) {
		return findPaginated("ADM_PROFILE", "PRF_SEQ", pageNumber, pageSize);
	}

	public List<AdmProfile> listByRange(int startInterval, int endInterval) {
		return listByRange("ADM_PROFILE", "PRF_SEQ", startInterval, endInterval);
	}

}
