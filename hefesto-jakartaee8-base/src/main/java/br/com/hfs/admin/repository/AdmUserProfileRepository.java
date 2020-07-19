package br.com.hfs.admin.repository;

import br.com.hfs.admin.model.AdmUserProfile;
import br.com.hfs.admin.model.AdmUserProfilePK;
import br.com.hfs.base.BaseRepository;

public class AdmUserProfileRepository extends BaseRepository<AdmUserProfile, AdmUserProfilePK> {

	private static final long serialVersionUID = 1L;
	
	public AdmUserProfileRepository() {
		super(AdmUserProfile.class);
	}
}
