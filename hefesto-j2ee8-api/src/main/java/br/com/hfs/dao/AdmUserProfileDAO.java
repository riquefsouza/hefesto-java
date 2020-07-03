package br.com.hfs.dao;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseDAO;
import br.com.hfs.model.AdmUserProfile;
import br.com.hfs.model.AdmUserProfilePK;

@Stateless
public class AdmUserProfileDAO extends BaseDAO<AdmUserProfile, AdmUserProfilePK> {

	private static final long serialVersionUID = 1L;
	
	public AdmUserProfileDAO() {
		super(AdmUserProfile.class);
	}
}
