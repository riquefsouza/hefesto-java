package br.com.hfs.admin.controller.dto;

public class TokenDTO {
	
	private String token;
	private String tipo;

	public TokenDTO() {
		super();
	}

	public TokenDTO(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}

}
