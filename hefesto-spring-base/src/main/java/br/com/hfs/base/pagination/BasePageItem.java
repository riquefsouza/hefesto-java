package br.com.hfs.base.pagination;

public class BasePageItem {

	private BasePageItemType pageItemType;

	private int index;

	private boolean active;

	public BasePageItem() {
		super();
	}

	public BasePageItem(BasePageItemType pageItemType, int index, boolean active) {
		super();
		this.pageItemType = pageItemType;
		this.index = index;
		this.active = active;
	}

	public BasePageItemType getPageItemType() {
		return pageItemType;
	}

	public void setPageItemType(BasePageItemType pageItemType) {
		this.pageItemType = pageItemType;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static class Builder {

		private BasePageItemType pageItemType;

		private int index;

		private boolean active;

		public Builder pageItemType(BasePageItemType pageItemType) {
			this.pageItemType = pageItemType;
			return this;
		}

		public Builder index(int index) {
			this.index = index;
			return this;
		}

		public Builder active(boolean active) {
			this.active = active;
			return this;
		}

		public BasePageItem build() {
			return new BasePageItem(pageItemType, index, active);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}
}
