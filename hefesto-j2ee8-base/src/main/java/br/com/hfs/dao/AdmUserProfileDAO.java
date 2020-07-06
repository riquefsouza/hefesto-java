package br.com.hfs.dao;

import br.com.hfs.base.BaseDAO;
import br.com.hfs.model.AdmUserProfile;
import br.com.hfs.model.AdmUserProfilePK;

public class AdmUserProfileDAO extends BaseDAO<AdmUserProfile, AdmUserProfilePK> {

	private static final long serialVersionUID = 1L;
	
	public AdmUserProfileDAO() {
		super(AdmUserProfile.class);
	}
}
