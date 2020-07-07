package br.com.hfs.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.admin.model.AdmUserProfile;
import br.com.hfs.admin.model.AdmUserProfilePK;

public interface AdmUserProfileRepository extends JpaRepository<AdmUserProfile, AdmUserProfilePK> {

}
