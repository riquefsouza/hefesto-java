/**
 * <p><b>HFS Framework</b></p>
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 2019
 */
package br.com.hfs.util.ldap;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class LdapAtributo.
 */
public class LdapAttribute implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private String id;

	/** The valor. */
	private String value;

	/**
	 * Instantiates a new ldap atributo.
	 */
	public LdapAttribute() {
		super();
	}

	/**
	 * Instantiates a new ldap atributo.
	 *
	 * @param id
	 *            the id
	 * @param value
	 *            the valor
	 */
	public LdapAttribute(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	/**
	 * Pega o the id.
	 *
	 * @return o the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Atribui o the id.
	 *
	 * @param id
	 *            o novo the id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Pega o the valor.
	 *
	 * @return o the valor
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Atribui o the valor.
	 *
	 * @param value
	 *            o novo the valor
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
