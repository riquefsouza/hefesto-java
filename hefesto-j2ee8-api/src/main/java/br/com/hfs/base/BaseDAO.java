package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

public abstract class BaseDAO<T, I extends Serializable> implements IBaseCrud<T, I> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	// private Logger log = Logger.getLogger(this.getClass().getName());

	@PersistenceContext
	protected EntityManager em;

	private Class<T> clazz;

	public BaseDAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Optional<T> findById(I id) {
		return (Optional<T>) Optional.ofNullable(em.find(clazz, id));
	}

	public List<T> findAll() {
		return em.createQuery("SELECT x FROM " + clazz.getName() + " x", clazz).getResultList();
	}

	@Transactional
	public T insert(T entity) {
		em.persist(entity);
		em.flush();
		return entity;
	}

	@Transactional
	public T update(T entity) {
		em.merge(entity);
		em.flush();
		return entity;
	}

	@Transactional
	public void delete(T entity) {
		if (!em.contains(entity)) {
			entity = em.merge(entity);
		}
		em.remove(entity);
		em.flush();
	}

	public List<T> findAll(int start, int max) {
		TypedQuery<T> query = em.createQuery("SELECT x FROM " + clazz.getName() + " x", clazz);
		if (start > 0) {
			query.setFirstResult(start);
		}
		if (max > 0) {
			query.setMaxResults(max);
		}
		return query.getResultList();
	}

	public Long count() {
		return (Long) em.createQuery("SELECT COUNT(x) FROM " + clazz.getName() + " x", Long.class).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findPaginated(String tableName, String pkName, int pageNumber, int pageSize) {
		String sql = "select * from (select tabela.*, " + pkName + " linha from (select * from " + tableName + " order by " + pkName + ") tabela "
				+ "where " + pkName + " < ((?1 * ?2) + 1 )) where linha >= (((?1-1) * ?2) + 1)";

		Query q = em.createNativeQuery(sql, clazz);
		q.setParameter(1, pageNumber);
		q.setParameter(2, pageSize);
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> listByRange(String tableName, String pkName, int startInterval, int endInterval) {
		String sql = "select * from (select tabela.*, " + pkName + " linha from (select * from " + tableName + " order by " + pkName + ") tabela "
				+ "where " + pkName + " <= ?2) where linha >= ?1";
		
		Query q = em.createNativeQuery(sql, clazz);
		q.setParameter(1, startInterval);
		q.setParameter(2, endInterval);
		
		return q.getResultList();
	}

	@Override
	public String getFieldById(String field, I id) {
		TypedQuery<T> q = em.createQuery("SELECT x." + field + " FROM " + clazz.getName() + " x WHERE x.id = ?1", clazz);
		q.setParameter(1, id);
		
		return (String) q.getSingleResult();
	}

	@Override
	public boolean thereIsFieldNew(String field, String isNew) {
		TypedQuery<T> q = em.createQuery("SELECT COUNT(x) FROM " + clazz.getName() + " x WHERE LOWER(x." + field + ") = ?1", clazz);
		q.setParameter(1, isNew.toLowerCase());
		Long existe = (Long) q.getSingleResult();
		return (existe > 0);
	}

	@Override
	public boolean thereIsFieldOld(String field, I id, String isNew) {
		String old = getFieldById(field, id);
		
		TypedQuery<T> q = em.createQuery("SELECT COUNT(x) FROM " + clazz.getName() + " x WHERE LOWER(x." + field + ") <> ?1 AND LOWER(x." + field + ") = ?2", clazz);
		q.setParameter(1, old.toLowerCase());
		q.setParameter(2, isNew.toLowerCase());
		Long existe = (Long) q.getSingleResult();
		return (existe > 0);
	}
}
