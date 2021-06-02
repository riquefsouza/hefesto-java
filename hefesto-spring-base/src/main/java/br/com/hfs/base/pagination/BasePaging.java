package br.com.hfs.base.pagination;

import java.util.ArrayList;
import java.util.List;

public class BasePaging {

	private static final int PAGINATION_STEP = 3;

    private boolean nextEnabled;
    private boolean prevEnabled;
    private int pageSize;
    private int pageNumber;
    
    private String pageSort;
    private int columnOrder;
    private String columnTitle;    

    private List<BasePageItem> items = new ArrayList<>();

    public BasePaging() {
		super();
	}

	public BasePaging(boolean nextEnabled, boolean prevEnabled, int pageSize, int pageNumber,
			String pageSort, int columnOrder, String columnTitle,
			List<BasePageItem> items) {
		super();
		this.nextEnabled = nextEnabled;
		this.prevEnabled = prevEnabled;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		
	    this.pageSort = pageSort;
	    this.columnOrder = columnOrder;
	    this.columnTitle = columnTitle;    
		
		this.items = items;
	}

	public void addPageItems(int from, int to, int pageNumber) {
        for (int i = from; i < to; i++) {
            items.add(BasePageItem.builder()
                              .active(pageNumber != i)
                              .index(i)
                              .pageItemType(BasePageItemType.PAGE)
                              .build());
        }
    }

    public void last(int pageSize) {
        items.add(BasePageItem.builder()
                          .active(false)
                          .pageItemType(BasePageItemType.DOTS)
                          .build());

        items.add(BasePageItem.builder()
                          .active(true)
                          .index(pageSize)
                          .pageItemType(BasePageItemType.PAGE)
                          .build());
    }

    public void first(int pageNumber) {
        items.add(BasePageItem.builder()
                          .active(pageNumber != 1)
                          .index(1)
                          .pageItemType(BasePageItemType.PAGE)
                          .build());

        items.add(BasePageItem.builder()
                          .active(false)
                          .pageItemType(BasePageItemType.DOTS)
                          .build());
    }

    public static BasePaging of(int totalPages, int pageNumber, int pageSize, String pageSort, int columnOrder, String columnTitle) {
        BasePaging paging = new BasePaging();
        paging.setPageSize(pageSize);
        paging.setNextEnabled(pageNumber != totalPages);
        paging.setPrevEnabled(pageNumber != 1);
        paging.setPageNumber(pageNumber);
        
        paging.setPageSort(pageSort);
        paging.setColumnOrder(columnOrder);
        paging.setColumnTitle(columnTitle);
        

        if (totalPages < PAGINATION_STEP * 2 + 6) {
            paging.addPageItems(1, totalPages + 1, pageNumber);

        } else if (pageNumber < PAGINATION_STEP * 2 + 1) {
            paging.addPageItems(1, PAGINATION_STEP * 2 + 4, pageNumber);
            paging.last(totalPages);

        } else if (pageNumber > totalPages - PAGINATION_STEP * 2) {
            paging.first(pageNumber);
            paging.addPageItems(totalPages - PAGINATION_STEP * 2 - 2, totalPages + 1, pageNumber);

        } else {
            paging.first(pageNumber);
            paging.addPageItems(pageNumber - PAGINATION_STEP, pageNumber + PAGINATION_STEP + 1, pageNumber);
            paging.last(totalPages);
        }

        return paging;
    }

	public boolean isNextEnabled() {
		return nextEnabled;
	}

	public void setNextEnabled(boolean nextEnabled) {
		this.nextEnabled = nextEnabled;
	}

	public boolean isPrevEnabled() {
		return prevEnabled;
	}

	public void setPrevEnabled(boolean prevEnabled) {
		this.prevEnabled = prevEnabled;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<BasePageItem> getItems() {
		return items;
	}

	public void setItems(List<BasePageItem> items) {
		this.items = items;
	}

	public String getPageSort() {
		return pageSort;
	}

	public void setPageSort(String pageSort) {
		this.pageSort = pageSort;
	}

	public int getColumnOrder() {
		return columnOrder;
	}

	public void setColumnOrder(int columnOrder) {
		this.columnOrder = columnOrder;
	}

	public String getColumnTitle() {
		return columnTitle;
	}

	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}
}
