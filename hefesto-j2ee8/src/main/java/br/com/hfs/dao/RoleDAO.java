package br.com.hfs.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.model.Role;

@Stateless
public class RoleDAO extends BaseDAO<Role> {

	public List<Role> findAll() {
		return em.createQuery("select r from Role r", Role.class).getResultList();
	}


}