package br.com.hfs.util.ldap;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.faces.context.FacesContext;

public class LdapConfig implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private int ldapSPort;
	
	private String ldapSProtocol;
	
	private int ldapPort;
	
	private String ldapConnectionType;
	
	/** The ldap filter. */
	private String ldapFilter;
	
	/** The ldap dn inativos. */
	private String ldapDnInactives;
	
	/** The ldap atributos. */
	private String ldapAttributes;
	
	/** The ldap atributo cod funcional. */
	private String ldapAttributeCodFunctional;
	
	/** The ldap server. */
	private String ldapServer;
	
	/** The ldap user senha. */
	private String ldapUserPassword;
	
	/** The ldap base DN. */
	private String ldapBaseDN;
	
	/** The ldap user DN. */
	private String ldapUserDN;
	
	/** The ldap base DN 1. */
	private String ldapBaseDN1;
	
	/** The ldap base DN 2. */
	private String ldapBaseDN2;
	
	/** The bundle. */
	private ResourceBundle bundle;

	/**
	 * Instantiates a new ldap config.
	 */
	public LdapConfig() {
		super();
		
		FacesContext context = FacesContext.getCurrentInstance();
		Locale locale = context.getViewRoot().getLocale();

		// locale = new Locale("pt", "BR");
		bundle = ResourceBundle.getBundle("ldap", locale);
	}

	/**
	 * Instantiates a new ldap config.
	 *
	 * @param bundle
	 *            the bundle
	 */
	public LdapConfig(ResourceBundle bundle) {
		this.bundle = bundle; 
	}
	
	/**
	 * Pega o the ldap S porta.
	 *
	 * @return o the ldap S porta
	 */
	public int getLdapSPort() {
		if (bundle == null)
			return ldapSPort;
		else	
			return Integer.parseInt(bundle.getString("ldapSPort"));
	}

	/**
	 * Pega o the ldap S protocolo.
	 *
	 * @return o the ldap S protocolo
	 */
	public String getLdapSProtocol() {
		if (bundle == null)
			return ldapSProtocol;
		else	
			return bundle.getString("ldapSProtocol");
	}

	/**
	 * Pega o the ldap porta.
	 *
	 * @return o the ldap porta
	 */
	public int getLdapPort() {
		if (bundle == null)
			return ldapPort;
		else	
			return Integer.parseInt(bundle.getString("ldapPort"));
	}

	/**
	 * Pega o the ldap tipo conexao.
	 *
	 * @return o the ldap tipo conexao
	 */
	public String getLdapConnectionType() {
		if (bundle == null)
			return ldapConnectionType;
		else	
			return bundle.getString("ldapConnectionType");
	}

	/**
	 * Pega o the ldap filter.
	 *
	 * @return o the ldap filter
	 */
	public String getLdapFilter() {
		if (bundle == null)
			return ldapFilter;
		else	
			return bundle.getString("ldapFilter");
	}

	/**
	 * Pega o the ldap dn inativos.
	 *
	 * @return o the ldap dn inativos
	 */
	public String getLdapDnInactives() {
		if (bundle == null)
			return ldapDnInactives;
		else	
			return bundle.getString("ldapDnInactives");
	}

	/**
	 * Pega o the ldap atributo cod funcional.
	 *
	 * @return o the ldap atributo cod funcional
	 */
	public String getLdapAttributeCodFunctional() {
		if (bundle == null)
			return ldapAttributeCodFunctional;
		else	
			return bundle.getString("ldapAttributeCodFunctional");
	}

	/**
	 * Pega o the ldap server.
	 *
	 * @return o the ldap server
	 */
	public String getLdapServer() {
		if (bundle == null)
			return ldapServer;
		else	
			return bundle.getString("ldapServer");
	}

	/**
	 * Pega o the ldap user senha.
	 *
	 * @return o the ldap user senha
	 */
	public String getLdapUserPassword() {
		if (bundle == null)
			return ldapUserPassword;
		else	
			return bundle.getString("ldappUserPassword");
	}

	/**
	 * Gets the ldap base DN 1.
	 *
	 * @return the ldap base DN 1
	 */
	public String getLdapBaseDN1() {
		if (bundle == null)
			return ldapBaseDN1;
		else	
			return bundle.getString("ldapBaseDN1");
	}

	/**
	 * Gets the ldap base DN 2.
	 *
	 * @return the ldap base DN 2
	 */
	public String getLdapBaseDN2() {
		if (bundle == null)
			return ldapBaseDN2;
		else	
			return bundle.getString("ldapBaseDN2");
	}

	/**
	 * Pega o the ldap user DN.
	 *
	 * @return o the ldap user DN
	 */
	public String getLdapUserDN() {
		if (bundle == null)
			return ldapUserDN;
		else	
			return bundle.getString("ldapUserDN");
	}
	
	/**
	 * Pega o the ldap atributos.
	 *
	 * @return o the ldap atributos
	 */
	public String getLdapAttributes() {
		if (bundle == null)
			return ldapAttributes;
		else	
			return bundle.getString("ldapAttributes");
	}

	/**
	 * Atribui o the ldap S porta.
	 *
	 * @param ldapSPorta
	 *            o novo the ldap S porta
	 */
	public void setLdapSPort(int ldapSPort) {
		this.ldapSPort = ldapSPort;
	}

	/**
	 * Atribui o the ldap S protocolo.
	 *
	 * @param ldapSProtocolo
	 *            o novo the ldap S protocolo
	 */
	public void setLdapSProtocol(String ldapSProtocol) {
		this.ldapSProtocol = ldapSProtocol;
	}

	/**
	 * Atribui o the ldap porta.
	 *
	 * @param ldapPorta
	 *            o novo the ldap porta
	 */
	public void setLdapPort(int ldapPort) {
		this.ldapPort = ldapPort;
	}

	/**
	 * Atribui o the ldap tipo conexao.
	 *
	 * @param ldapTipoConexao
	 *            o novo the ldap tipo conexao
	 */
	public void setLdapConnectionType(String ldapConnectionType) {
		this.ldapConnectionType = ldapConnectionType;
	}

	/**
	 * Atribui o the ldap filter.
	 *
	 * @param ldapFilter
	 *            o novo the ldap filter
	 */
	public void setLdapFilter(String ldapFilter) {
		this.ldapFilter = ldapFilter;
	}

	/**
	 * Atribui o the ldap dn inativos.
	 *
	 * @param ldapDnInativos
	 *            o novo the ldap dn inativos
	 */
	public void setLdapDnInactives(String ldapDnInactives) {
		this.ldapDnInactives = ldapDnInactives;
	}

	/**
	 * Atribui o the ldap atributos.
	 *
	 * @param ldapAtributos
	 *            o novo the ldap atributos
	 */
	public void setLdapAttributes(String ldapAttributes) {
		this.ldapAttributes = ldapAttributes;
	}

	/**
	 * Atribui o the ldap atributo cod funcional.
	 *
	 * @param ldapAtributoCodFuncional
	 *            o novo the ldap atributo cod funcional
	 */
	public void setLdapAttributeCodFunctional(String ldapAttributeCodFunctional) {
		this.ldapAttributeCodFunctional = ldapAttributeCodFunctional;
	}

	/**
	 * Atribui o the ldap server.
	 *
	 * @param ldapServer
	 *            o novo the ldap server
	 */
	public void setLdapServer(String ldapServer) {
		this.ldapServer = ldapServer;
	}

	/**
	 * Atribui o the ldap user senha.
	 *
	 * @param ldapUserSenha
	 *            o novo the ldap user senha
	 */
	public void setLdapUserPassword(String ldapUserPassword) {
		this.ldapUserPassword = ldapUserPassword;
	}

	/**
	 * Sets the ldap base DN 1.
	 *
	 * @param ldapBaseDN1 the new ldap base DN 1
	 */
	public void setLdapBaseDN1(String ldapBaseDN1) {
		this.ldapBaseDN1 = ldapBaseDN1;
	}

	/**
	 * Sets the ldap base DN 2.
	 *
	 * @param ldapBaseDN2 the new ldap base DN 2
	 */
	public void setLdapBaseDN2(String ldapBaseDN2) {
		this.ldapBaseDN2 = ldapBaseDN2;
	}

	/**
	 * Atribui o the ldap user DN.
	 *
	 * @param ldapUserDN
	 *            o novo the ldap user DN
	 */
	public void setLdapUserDN(String ldapUserDN) {
		this.ldapUserDN = ldapUserDN;
	}

	/**
	 * Gets the ldap base DN.
	 *
	 * @return the ldap base DN
	 */
	public String getLdapBaseDN() {
		return ldapBaseDN;
	}

	/**
	 * Sets the ldap base DN.
	 *
	 * @param ldapBaseDN the new ldap base DN
	 */
	public void setLdapBaseDN(String ldapBaseDN) {
		this.ldapBaseDN = ldapBaseDN;
	}

}
