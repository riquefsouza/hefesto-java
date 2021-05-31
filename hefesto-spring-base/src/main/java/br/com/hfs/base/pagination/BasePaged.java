package br.com.hfs.base.pagination;

import org.springframework.data.domain.Page;

public class BasePaged<T> {

    private Page<T> page;

    private BasePaging paging;

	public BasePaged() {
		super();
	}

	public BasePaged(Page<T> page, BasePaging paging) {
		super();
		this.page = page;
		this.paging = paging;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public BasePaging getPaging() {
		return paging;
	}

	public void setPaging(BasePaging paging) {
		this.paging = paging;
	}

}
