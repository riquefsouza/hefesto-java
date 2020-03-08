package br.com.hfs.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.hfs.model.User;

@Stateless
public class UserDAO extends BaseDAO<User> {

	public List<User> findAll() {
		return em.createQuery("select u from User u", User.class).getResultList();
	}


}
