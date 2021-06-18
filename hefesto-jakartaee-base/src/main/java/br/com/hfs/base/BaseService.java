package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.hfs.ApplicationUtil;
import br.com.hfs.util.exceptions.TransactionException;

public abstract class BaseService<T, I extends Serializable, C extends BaseRepository<T, I>>
		implements IBaseCrud<T, I> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	//@Inject
	protected Logger log = LogManager.getLogger(BaseService.class);;

	@Inject
	protected C repository;

	@Inject
	protected ApplicationUtil applicationUtil;

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
			return repository.insert(bean);
		} catch (Exception e) {
			throw new TransactionException(log, ERROR_INSERT + e.getMessage(), e);
		}
	}

	@Override
	public T update(T bean) throws TransactionException {
		try {
			return repository.update(bean);
		} catch (Exception e) {
			throw new TransactionException(log, ERROR_UPDATE + e.getMessage(), e);
		}
	}
	
	@Override
	public void delete(T bean) throws TransactionException {
		try {
			repository.delete(bean);
		} catch (Exception e) {
			throw new TransactionException(log, ERROR_DELETE + e.getMessage(), e);
		}
	}
	
	@Override
	public void deleteById(I id) throws TransactionException {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new TransactionException(log, ERROR_DELETE + e.getMessage(), e);
		}
	}
	
	@Transactional
	public int directDeleteById(I id) throws TransactionException {
		try {
			return repository.directDeleteById(id);
		} catch (Exception e) {
			throw new TransactionException(log, ERROR_DELETE + e.getMessage(), e);
		}
	}
	
	@Override
	public List<T> findAll(int start, int max) {
		return repository.findAll(start, max);
	}

	@Override
	public Long count() {
		return repository.count();
	}

	abstract public List<T> findPaginated(int pageNumber, int pageSize);

	abstract public List<T> listByRange(int startInterval, int endInterval);

	@Override
	public String getFieldById(String field, I id) {
		return repository.getFieldById(field, id);
	}

	@Override
	public boolean thereIsFieldNew(String field, String isNew) {
		return repository.thereIsFieldNew(field, isNew);
	}

	@Override
	public boolean thereIsFieldOld(String field, I id, String isNew) {
		return repository.thereIsFieldOld(field, id, isNew);
	}

	@Override
	public List<T> insert(List<T> listEntity) throws TransactionException {
		try {
			return repository.insert(listEntity);
		} catch (Exception e) {
			throw new TransactionException(log, ERROR_INSERT + e.getMessage(), e);
		}
	}

	@Override
	public List<T> update(List<T> listEntity) throws TransactionException {
		try {
			return repository.update(listEntity);
		} catch (Exception e) {
			throw new TransactionException(log, ERROR_UPDATE + e.getMessage(), e);
		}
	}

	@Override
	public void delete(List<T> listEntity) throws TransactionException {
		try {
			repository.delete(listEntity);
		} catch (Exception e) {
			throw new TransactionException(log, ERROR_DELETE + e.getMessage(), e);
		}
	}

	public C getRepository() {
		return repository;
	}
	
}
