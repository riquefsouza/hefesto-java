package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.util.TransactionException;

public abstract class BaseService<T, I extends Serializable, C extends JpaRepository<T, I>>
		implements IBaseCrud<T, I> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	//@Inject
	protected Logger log = LogManager.getLogger(BaseService.class);;

	@Autowired
	protected C repository;

	//@Inject
	//protected AplicacaoUtil aplicacaoUtil;
	
	@Autowired
	protected EntityManager em;
	
	private Class<T> clazz;

	public BaseService(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public Class<T> getClazz() {
		return clazz;
	}

	@Override
	public Optional<T> findById(I id) {
		return repository.findById(id);
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public T insert(T bean) throws TransactionException {
		try {
			return repository.saveAndFlush(bean);
		} catch (Exception e) {
			throw new TransactionException(log, ERRO_INSERT + e.getMessage(), e);
		}
	}

	@Override
	public T update(T bean) throws TransactionException {
		try {
			return repository.saveAndFlush(bean);
		} catch (Exception e) {
			throw new TransactionException(log, ERRO_UPDATE + e.getMessage(), e);
		}
	}
	
	@Override
	public T saveById(T bean, I id) throws TransactionException {
		try {
			if (repository.existsById(id)) {
				return repository.saveAndFlush(bean);
			} else 
				return null;
		} catch (Exception e) {
			throw new TransactionException(log, ERRO_SALVAR + e.getMessage(), e);
		}
	}
	
	@Override
	public void delete(T bean) throws TransactionException {
		try {
	        if (!em.contains(bean)){
	            bean = em.merge(bean);
	        }	        
	        repository.delete(bean);
	        repository.flush();

		} catch (Exception e) {
			throw new TransactionException(log, ERRO_DELETE + e.getMessage(), e);
		}
	}
	
	@Override
	public void deleteById(I id) throws TransactionException {
		try {
			Optional<T> obean = repository.findById(id);
			if (obean.isPresent()) {
				T bean = obean.get();
				
		        if (!em.contains(bean)){
		            bean = em.merge(bean);
		        }
				repository.deleteById(id);
				repository.flush();
			}
		} catch (Exception e) {
			throw new TransactionException(log, ERRO_DELETE + e.getMessage(), e);
		}
	}
	
	@Transactional
	public int directDeleteById(I id) throws TransactionException {
		try {
			Query query = em.createQuery("DELETE FROM " + clazz.getName() + " m WHERE m.id = ?1");
			query.setParameter(1, id);
			 
			return query.executeUpdate(); 
		} catch (Exception e) {
			throw new TransactionException(log, ERRO_DELETE + e.getMessage(), e);
		}
	}
	
	@Override
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

	@Override
	public Long count() {
		return repository.count();
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

	@Override
	public List<T> insert(List<T> listEntity) throws TransactionException {
		try {
			return repository.saveAll(listEntity);
		} catch (Exception e) {
			throw new TransactionException(log, ERRO_INSERT + e.getMessage(), e);
		}
	}

	@Override
	public List<T> update(List<T> listEntity) throws TransactionException {
		try {
			return repository.saveAll(listEntity);
		} catch (Exception e) {
			throw new TransactionException(log, ERRO_UPDATE + e.getMessage(), e);
		}
	}

	@Override
	public void delete(List<T> listEntity) throws TransactionException {
		try {
			repository.deleteAll(listEntity);
		} catch (Exception e) {
			throw new TransactionException(log, ERRO_DELETE + e.getMessage(), e);
		}
	}
	
}
