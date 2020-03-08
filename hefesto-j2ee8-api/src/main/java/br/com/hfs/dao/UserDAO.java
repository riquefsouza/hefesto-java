package br.com.hfs.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.model.User;

@Stateless
public class UserDAO extends BaseDAO<User> {

	public List<User> findAll() {
		return em.createQuery("select x from User x", User.class).getResultList();
	}


}
