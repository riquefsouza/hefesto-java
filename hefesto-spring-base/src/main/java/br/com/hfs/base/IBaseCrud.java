package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.TransactionException;

import br.com.hfs.base.pagination.BasePaged;

public interface IBaseCrud <T, I extends Serializable> extends Serializable {

	public static final String ERROR_INSERT = "Transaction error to insert: ";
	
	public static final String ERROR_UPDATE = "Transaction error to update: ";
	
	public static final String ERROR_DELETE = "Transaction error to delete: ";
	
	public static final String ERROR_SAVE = "Transaction error to save: ";
	
	Optional<T> findById(I id);	

	List<T> findAll();
	
	Page<T> findAll(Pageable pageable);
	
	BasePaged<T> getPage(int pageNumber, int size, Sort sort, String paramSort, int columnOrder, String columnTitle);

	T insert(T bean) throws TransactionException;

	T update(T bean) throws TransactionException;

	void delete(T bean) throws TransactionException;
	
	void deleteById(I id) throws TransactionException;
	
	int directDeleteById(I id) throws TransactionException;
	
	List<T> findAll(int start, int max);
	
	Long count();
	
	//List<T> findPaginated(int pageNumber, int pageSize);

	//List<T> listByRange(int startInterval, int endInterval);

	String getFieldById(String field, I id);
	
	boolean thereIsFieldNew(String field, String isNew);
	
	boolean thereIsFieldOld(String field, I id, String isNew);
	
	List<T> insert(List<T> listEntity) throws TransactionException;
	
	List<T> update(List<T> listEntity) throws TransactionException;
	
	void delete(List<T> listEntity) throws TransactionException;

}
