package br.com.hfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.model.AdmUserProfile;
import br.com.hfs.model.AdmUserProfilePK;

public interface AdmUserProfileRepository extends JpaRepository<AdmUserProfile, AdmUserProfilePK> {

}
