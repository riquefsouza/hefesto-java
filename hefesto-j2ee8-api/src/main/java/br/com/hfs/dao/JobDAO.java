package br.com.hfs.dao;

import javax.ejb.Stateless;

import br.com.hfs.model.Job;

import java.util.List;

@Stateless
public class JobDAO extends BaseDAO<Job> {

	public List<Job> findAll() {
		return em.createQuery("select j from Job j", Job.class).getResultList();
	}

}
