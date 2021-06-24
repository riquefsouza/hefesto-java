package br.com.hfs.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

public abstract class BaseRepository<T, I extends Serializable> implements IBaseCrud<T, I> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	protected EntityManager em;

	private Class<T> clazz;

	public BaseRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Class<T> getClazz() {
		return clazz;
	}
	
	public Optional<T> findById(I id) {
		return (Optional<T>) Optional.ofNullable(em.find(clazz, id));
	}

	public List<T> findAll() {
		return em.createQuery("SELECT x FROM " + clazz.getName() + " x", clazz).getResultList();
	}

	public Integer queryIntegerSingle(String nameQuery, Object... lparams) {
		TypedQuery<Integer> query = em.createNamedQuery(nameQuery, Integer.class);
		for(int i = 0; i < lparams.length; i++){
			Object param = lparams[i];
			query.setParameter(i+1, param);
		}
		return query.getSingleResult();
	}

	public Long queryLongSingle(String nameQuery, Object... lparams) {
		TypedQuery<Long> query = em.createNamedQuery(nameQuery, Long.class);
		for(int i = 0; i < lparams.length; i++){
			Object param = lparams[i];
			query.setParameter(i+1, param);
		}
		return query.getSingleResult();
	}

	public List<Long> queryLongList(String nameQuery, Object... lparams) {
		TypedQuery<Long> query = em.createNamedQuery(nameQuery, Long.class);
		for(int i = 0; i < lparams.length; i++){
			Object param = lparams[i];
			query.setParameter(i+1, param);
		}
		return query.getResultList();
	}

	public String queryStringSingle(String nameQuery, Object... lparams) {
		TypedQuery<String> query = em.createNamedQuery(nameQuery, String.class);
		for(int i = 0; i < lparams.length; i++){
			Object param = lparams[i];
			query.setParameter(i+1, param);
		}
		return query.getSingleResult();
	}

	public <X> TypedQuery<X> query(Class<X> resultClass, String nameQuery, Object... lparams) {
		TypedQuery<X> query = em.createNamedQuery(nameQuery, resultClass);
		for(int i = 0; i < lparams.length; i++){
			Object param = lparams[i];
			query.setParameter(i+1, param);
		}
		return query;
	}

	public List<T> queryList(String nameQuery) {
		TypedQuery<T> query = em.createNamedQuery(nameQuery, this.clazz);
		return query.getResultList();
	}
	
	public List<T> queryList(String nameQuery, Object... lparams) {
		TypedQuery<T> query = em.createNamedQuery(nameQuery, this.clazz);
		for(int i = 0; i < lparams.length; i++){
			Object param = lparams[i];
			query.setParameter(i+1, param);
		}
		return query.getResultList();
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
	
	@Transactional
	public void deleteById(I id) {
		Optional<T> entity = findById(id);
		if (entity.isPresent()) {
			delete(entity.get());
		}
	}
	
	@Transactional
	public int directDeleteById(I id) {	
		Query query = em.createQuery("DELETE FROM " + clazz.getName() + " m WHERE m.id = ?1");
		query.setParameter(1, id);
		 
		return query.executeUpdate(); 
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
		try {
			TypedQuery<String> q = em.createQuery("SELECT x." + field + " FROM " + clazz.getName() + " x WHERE x.id = ?1", String.class);
			q.setParameter(1, id);
			
			return (String) q.getSingleResult();
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public boolean thereIsFieldNew(String field, String isNew) {
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(x) FROM " + clazz.getName() + " x WHERE LOWER(x." + field + ") = ?1", Long.class);
		q.setParameter(1, isNew.toLowerCase());
		Long existe = q.getSingleResult();
		return (existe > 0);
	}

	@Override
	public boolean thereIsFieldOld(String field, I id, String isNew) {
		String old = getFieldById(field, id);
		
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(x) FROM " + clazz.getName() + " x WHERE LOWER(x." + field + ") <> ?1 AND LOWER(x." + field + ") = ?2", Long.class);
		q.setParameter(1, old.toLowerCase());
		q.setParameter(2, isNew.toLowerCase());
		Long existe = q.getSingleResult();
		return (existe > 0);
	}
	
	@Transactional
	public List<T> insert(List<T> listEntity) {
		List<T> listOut = new ArrayList<T>();
		listEntity.forEach(entity -> {
			listOut.add(insert(entity));
		});
		return listOut;
	}
	
	@Transactional
	public List<T> update(List<T> listEntity) {
		List<T> listOut = new ArrayList<T>();
		listEntity.forEach(entity -> {
			listOut.add(update(entity));
		});
		return listOut;
	}
	
	@Transactional
	public void delete(List<T> listEntity) {
		listEntity.forEach(entity -> delete(entity));
	}
}
