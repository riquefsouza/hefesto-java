package br.com.hfs.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.admin.model.AdmUserProfile;

public interface AdmUserProfileRepository extends JpaRepository<AdmUserProfile, Long> {

}
