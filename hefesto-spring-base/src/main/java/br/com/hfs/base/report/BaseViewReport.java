/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2017
 */
package br.com.hfs.base.report;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hfs.base.BaseService;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseViewRelatorio.
 *
 * @param <T>
 *            the generic type
 * @param <I>
 *            the generic type
 * @param <B>
 *            the generic type
 */
public abstract class BaseViewReport<T, I extends Serializable, 
	B extends BaseService<T, I, ? extends JpaRepository<T, I>>>
		extends BaseViewReportController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The business controller. */
	@Autowired
	private B businessController;

	/** The entidade. */
	private T entidade;

	/**
	 * Instantiates a new base view relatorio.
	 *
	 * @param entidade the entidade
	 */
	public BaseViewReport(T entidade) {
		super();
		
		this.entidade = entidade;
	}

	/**
	 * Inicia o.
	 */
	@PostConstruct
	public void init() {
		super.init();
	}

	/**
	 * Pega o the entidade.
	 *
	 * @return o the entidade
	 */
	public T getEntidade() {
		return this.entidade;
	}

	/**
	 * Atribui o the entidade.
	 *
	 * @param entidade
	 *            o novo the entidade
	 */
	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	/**
	 * Pega o the business controller.
	 *
	 * @return o the business controller
	 */
	public B getBusinessController() {
		return businessController;
	}
}
