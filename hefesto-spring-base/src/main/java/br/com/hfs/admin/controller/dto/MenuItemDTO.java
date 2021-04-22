package br.com.hfs.admin.controller.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MenuItemDTO implements Serializable, Comparable<MenuItemDTO> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String label;
	
	@JsonInclude(Include.NON_NULL)
	private String routerLink;
	
	@JsonInclude(Include.NON_NULL)
	private String url;
	
	@JsonInclude(Include.NON_NULL)
	private String to;
	
	@JsonInclude(Include.NON_NULL)
	private List<MenuItemDTO> item;

	public MenuItemDTO() {
		this.item = new ArrayList<MenuItemDTO>();
		clean();
	}

	public MenuItemDTO(String label, String url) {
		super();
		this.label = label;
		this.url = url;
		this.routerLink = url;
		this.to = url;
	}
	
	public MenuItemDTO(String label, String url, List<MenuItemDTO> item) {
		super();
		this.label = label;
		this.url = url;
		this.routerLink = url;
		this.to = url;
		this.item = item;
	}

	public void clean() {
		this.label = "";
		this.routerLink = "";
		this.url = "";
		this.to = "";
		this.item.clear();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRouterLink() {
		return routerLink;
	}

	public void setRouterLink(String routerLink) {
		this.routerLink = routerLink;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<MenuItemDTO> getItem() {
		return item;
	}

	public void setItem(List<MenuItemDTO> item) {
		this.item = item;
	}

	@Override
	public int compareTo(MenuItemDTO m) {
		return getLabel().compareTo(m.getLabel());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItemDTO other = (MenuItemDTO) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

}
