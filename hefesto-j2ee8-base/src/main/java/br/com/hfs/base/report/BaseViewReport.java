package br.com.hfs.base.report;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.hfs.base.BaseRepository;
import br.com.hfs.base.BaseService;
import br.com.hfs.util.interceptors.HandlingExpectedErrors;

@Named
@ViewScoped
@HandlingExpectedErrors
public class BaseViewReport<T, I extends Serializable, B extends BaseService<T, I, ? extends BaseRepository<T, I>>>
		extends BaseViewReportController implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Inject
	private B service;

	@Inject
	private T entity;

	public BaseViewReport() {
		super();
	}

	@PostConstruct
	public void init() {
		super.init();
	}

	public T getEntity() {
		return this.entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public B getService() {
		return service;
	}

}
