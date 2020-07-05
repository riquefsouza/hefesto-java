package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.TransactionException;

import br.com.hfs.util.ExceptionUtil;

public abstract class BaseService<T, I extends Serializable, C extends BaseDAO<T, I>>
		implements IBaseCrud<T, I> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	//@Inject
	protected Logger log = LogManager.getLogger(BaseService.class);;

	@Inject
	protected C repository;

	//@Inject
	//protected AplicacaoUtil aplicacaoUtil;

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
			String msg = ERRO_INSERT + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
		}
	}

	@Override
	public T update(T bean) throws TransactionException {
		try {
			return repository.update(bean);
		} catch (Exception e) {
			String msg = ERRO_UPDATE + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
		}
	}
	
	@Override
	public T saveById(T bean, I id) throws TransactionException {
		try {
			return repository.saveById(bean, id);
		} catch (Exception e) {
			String msg = ERRO_SALVAR + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
		}
	}
	
	@Override
	public void delete(T bean) throws TransactionException {
		try {
			repository.delete(bean);
		} catch (Exception e) {
			String msg = ERRO_DELETE + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
		}
	}
	
	@Override
	public void deleteById(I id) throws TransactionException {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			String msg = ERRO_DELETE + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
		}
	}
	
	@Transactional
	public int directDeleteById(I id) throws TransactionException {
		try {
			return repository.directDeleteById(id);
		} catch (Exception e) {
			String msg = ERRO_DELETE + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
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

	//abstract public List<T> findPaginated(int pageNumber, int pageSize);

	//abstract public List<T> listByRange(int startInterval, int endInterval);

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
			String msg = ERRO_INSERT + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
		}
	}

	@Override
	public List<T> update(List<T> listEntity) throws TransactionException {
		try {
			return repository.update(listEntity);
		} catch (Exception e) {
			String msg = ERRO_UPDATE + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
		}
	}

	@Override
	public void delete(List<T> listEntity) throws TransactionException {
		try {
			repository.delete(listEntity);
		} catch (Exception e) {
			String msg = ERRO_DELETE + e.getMessage();
			ExceptionUtil.getErrors(log, e, msg, true);
			throw new TransactionException(msg, e);
		}
	}
	
}
