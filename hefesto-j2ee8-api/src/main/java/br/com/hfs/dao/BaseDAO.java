package br.com.hfs.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.hfs.model.BaseEntity;

public abstract class BaseDAO<T extends BaseEntity<?>> {

	//private Logger log = Logger.getLogger(this.getClass().getName());

	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	public Optional<T> findById(T entity) {
		return (Optional<T>) Optional.ofNullable(em.find(entity.getClass(), entity.getId()));
	}

	public T save(T entity) {
		em.persist(entity);
		em.flush();
		return entity;
	}

	public T update(T entity) {
		em.merge(entity);
		em.flush();
		return entity;
	}

	public void remove(T entity) {
		em.remove(entity);
		em.flush();
	}
}
