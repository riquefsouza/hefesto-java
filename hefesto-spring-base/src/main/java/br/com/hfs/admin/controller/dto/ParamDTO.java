package br.com.hfs.admin.controller.dto;

public class ParamDTO {

	private String key;

	private String value;
	
	public ParamDTO() {
		super();
		this.key = "";
		this.value = "";
	}

	public ParamDTO(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ParamDTO [key=" + key + ", value=" + value + "]";
	}

}
