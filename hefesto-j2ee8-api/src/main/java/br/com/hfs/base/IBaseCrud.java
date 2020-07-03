package br.com.hfs.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.TransactionException;

public interface IBaseCrud <T, I extends Serializable> extends Serializable {

	/** The Constant ERRO_INSERT. */
	public static final String ERRO_INSERT = "Erro de Transação ao Incluir: ";
	
	/** The Constant ERRO_UPDATE. */
	public static final String ERRO_UPDATE = "Erro de Transação ao Alterar: ";
	
	/** The Constant ERRO_DELETE. */
	public static final String ERRO_DELETE = "Erro de Transação ao Excluir: ";
	
	Optional<T> findById(I id);	

	List<T> findAll();

	T insert(T bean) throws TransactionException;

	T update(T bean) throws TransactionException;

	void delete(T bean) throws TransactionException;
	
	List<T> findAll(int start, int max);
	
	Long count();
	
	//List<T> findPaginated(int pageNumber, int pageSize);

	//List<T> listByRange(int startInterval, int endInterval);

	String getFieldById(String field, I id);
	
	boolean thereIsFieldNew(String field, String isNew);
	
	boolean thereIsFieldOld(String field, I id, String isNew);

}
