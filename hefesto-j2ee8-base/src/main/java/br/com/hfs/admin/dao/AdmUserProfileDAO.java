package br.com.hfs.admin.dao;

import br.com.hfs.admin.model.AdmUserProfile;
import br.com.hfs.admin.model.AdmUserProfilePK;
import br.com.hfs.base.BaseDAO;

public class AdmUserProfileDAO extends BaseDAO<AdmUserProfile, AdmUserProfilePK> {

	private static final long serialVersionUID = 1L;
	
	public AdmUserProfileDAO() {
		super(AdmUserProfile.class);
	}
}
