package br.com.hfs.admin.service;

import org.springframework.stereotype.Service;

import br.com.hfs.admin.model.AdmPageProfile;
import br.com.hfs.admin.repository.AdmPageProfileRepository;
import br.com.hfs.base.BaseService;

@Service
public class AdmPageProfileService extends BaseService<AdmPageProfile, Long, AdmPageProfileRepository> {

	private static final long serialVersionUID = 1L;

	public AdmPageProfileService() {
		super(AdmPageProfile.class);
	}
	
}
