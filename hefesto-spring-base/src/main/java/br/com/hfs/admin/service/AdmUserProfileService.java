package br.com.hfs.admin.service;

import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmUserProfile;
import br.com.hfs.admin.repository.AdmUserProfileRepository;
import br.com.hfs.base.BaseService;

@Service
public class AdmUserProfileService extends BaseService<AdmUserProfile, Long, AdmUserProfileRepository> {

	private static final long serialVersionUID = 1L;

	public AdmUserProfileService() {
		super(AdmUserProfile.class);
	}
	
}
