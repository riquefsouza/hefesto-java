package br.com.hfs.util.ldap;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LdapUtil implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private Logger log;

	/** The config. */
	private LdapConfig config;
	
	private List<LdapAttribute> listLdapAttribute;

	public LdapUtil() {
		super();
		this.log = LogManager.getLogger(LdapUtil.class);
		this.listLdapAttribute = new ArrayList<LdapAttribute>();
	}

	/**
	 * Configurar.
	 *
	 * @param config
	 *            the config
	 */
	public void configurar(LdapConfig config){
		this.config = config;
	}
	
	/**
	 * Configurar LDAP.
	 *
	 * @return the ldap connection config
	 */
	private LdapConnectionConfig configurarLDAP() {
		LdapConnectionConfig sslConfig = new LdapConnectionConfig();
		sslConfig.setLdapHost(config.getLdapServer());
		sslConfig.setLdapPort(getPortaLDAP());

		if ("LDAPS".compareTo(config.getLdapConnectionType()) == 0) {
			sslConfig.setEnabledProtocols(new String[] { "SSLv2Hello", "SSLv3", "TLSv1" });
			sslConfig.setSslProtocol(config.getLdapSProtocol());
			sslConfig.setUseSsl(true);
		}
		return sslConfig;
	}

	/**
	 * Gets the porta LDAP.
	 *
	 * @return the porta LDAP
	 */
	private int getPortaLDAP() {
		if ("LDAP".compareTo(config.getLdapConnectionType()) == 0) {
			return config.getLdapPort();
		}

		if ("LDAPS".compareTo(config.getLdapConnectionType()) == 0) {
			return config.getLdapSPort();
		}

		return -1;
	}

	/**
	 * Fechar conexao LDAP.
	 *
	 * @param connection
	 *            the connection
	 * @throws LdapConfigurationException
	 *             the ldap configuracao exception
	 */
	private void fecharConexaoLDAP(LdapConnection connection) 
			throws LdapConfigurationException {
		try {
			connection.close();
			log.info("Connection closed LDAP");
		} catch (IOException e) {
			throw new LdapConfigurationException(e.getMessage());
		}
	}

	public boolean login(String usuario, String senha)
			throws LdapConfigurationException, LdapLoginException {
		boolean retorno = false;
		Entry info;
		EntryCursor cursor;
		String dnUsuario;
		Dn dnInativos;
		LdapConnectionConfig sslConfig = configurarLDAP();
		LdapNetworkConnection conexao = new LdapNetworkConnection(sslConfig);
		String filter = config.getLdapFilter().replace("USER", usuario);
		String atributos[];

		atributos = config.getLdapAttributes().split(",");

		try {
			conexao.bind(config.getLdapUserDN(), config.getLdapUserPassword());
		} catch (LdapException e) {
			fecharConexaoLDAP(conexao);
			throw new LdapConfigurationException(log,
					"Communication with the LDAP server failed, check the settings on ldap.properties", e);
		}					

		try {
			if (conexao.isConnected()){
			
				try {
					cursor = conexao.search(config.getLdapBaseDN(), filter, SearchScope.SUBTREE, atributos);
					if (cursor.next()) {
						info = cursor.get();

						dnInativos = new Dn(config.getLdapDnInactives());
						// Valida se o usuário não está inativo.
						if (dnInativos.isAncestorOf(info.getDn())) {
							throw new LdapLoginException(log,
									"User authentication failure. User is inactive.");
						}

						if (info.size() > 0) {
							dnUsuario = info.getDn().toString();

							try {
								conexao.bind(dnUsuario, senha);
								if (conexao.isAuthenticated()){
									retorno = true;
									
									for (Iterator<Attribute> iterator = info.iterator(); iterator
											.hasNext();) {
										Attribute entrada = iterator.next();
										listLdapAttribute.add(new LdapAttribute(entrada.getId(), entrada.getString()));
										log.info(entrada.getId() + ": " + entrada.getString());
									}				
									
									conexao.unBind();
								}
							} catch (LdapException e) {
								throw new LdapLoginException(log,
										"User authentication failure, check login and password", e);
							}
						}
					}
				} catch (LdapException e) {
					throw new LdapConfigurationException(log,
							"LDAP search failed, check the parameters of the ldap.properties", e);
				} catch (CursorException e) {
					throw new LdapConfigurationException(log,
							"LDAP search failed, check the parameters of the ldap.properties", e);
				}				
				
			} else {
				throw new LdapConfigurationException(log, "Connection error!");
			}
			
		} finally {
			fecharConexaoLDAP(conexao);
		}
		
		return retorno;
	}

	public List<LdapAttribute> getAttributes(String usuario)
			throws LdapConfigurationException, LdapLoginException {
		List<LdapAttribute> lista = new ArrayList<LdapAttribute>();
		LdapConnectionConfig sslConfig = configurarLDAP();
		LdapNetworkConnection connection = new LdapNetworkConnection(sslConfig);
		String filter = config.getLdapFilter().replace("USER", usuario);
		String atributos[];

		atributos = config.getLdapAttributes().split(",");

		try {
			connection.bind(config.getLdapUserDN(), config.getLdapUserPassword());
		} catch (LdapException e) {
			fecharConexaoLDAP(connection);
			throw new LdapConfigurationException(
					"Communication with the LDAP server failed, check the settings on ldap.properties");
		}
		try {
			EntryCursor cursor = connection.search(config.getLdapBaseDN(), filter, SearchScope.SUBTREE, atributos);
			Entry entry;
			if (cursor.next()) {
				entry = cursor.get();
				Dn dnInativos = new Dn(config.getLdapDnInactives());
				// Valida se o usuário não está inativo.
				if (dnInativos.isAncestorOf(entry.getDn())) {
					throw new LdapLoginException(
							"User authentication failure. User is inactive.");
				}
				
				for (Iterator<Attribute> iterator = entry.iterator(); iterator
						.hasNext();) {
					Attribute entrada = iterator.next();
					lista.add(new LdapAttribute(entrada.getId(), entrada.getString()));
				}				
				
			} else {
				throw new LdapLoginException(
						"User authentication failure, check login and password");

			}
		} catch (LdapException e) {
			throw new LdapLoginException(
					"User authentication failure, check login and password");
		} catch (CursorException e) {
			throw new LdapConfigurationException(
					"LDAP search failed, check the parameters of the ldap.properties");
		} finally {
			fecharConexaoLDAP(connection);
		}
		
		return lista;
	}
	/*
	private String getDescriptionEstagiario(String desc){
		this.cpf = false;
		String dsc = desc.toUpperCase().trim();
		
		if (dsc.contains("CPF") || dsc.contains("CPD") || dsc.contains("ESTAG") || dsc.contains("MAT")){
			
			if (dsc.contains("CPF") && dsc.contains("MAT")){
				int espaco = dsc.indexOf(' ');
				int traco = dsc.indexOf('-');
				dsc = dsc.substring(espaco + 1,traco);
				this.cpf = true;
			} else if (dsc.contains("ESTAG") && dsc.contains("/")) {
				int traco = dsc.indexOf('-');
				int espaco = dsc.indexOf(' ');
				dsc = dsc.substring(traco + 1,espaco);
				this.cpf = false;
			} else {
				//Somente numeros
				dsc = dsc.replaceAll("[^0-9]", "");
				
				if (dsc.length()==11){
					this.cpf = true;
				}
			}
			
		} else {
			//Somente numeros
			dsc = dsc.replaceAll("[^0-9]", "");        
			if (dsc.length()==11){
				this.cpf = true;
			}
		}
		
		return dsc.trim();
	}
	*/
	/**
	 * Pega o the lista ldap atributo.
	 *
	 * @return o the lista ldap atributo
	 */
	public List<LdapAttribute> getListaLdapAttribute() {
		return listLdapAttribute;
	}

	/**
	 * Atribui o the lista ldap atributo.
	 *
	 * @param listLdapAttribute
	 *            o novo the lista ldap atributo
	 */
	public void setListaLdapAttribute(List<LdapAttribute> listLdapAttribute) {
		this.listLdapAttribute = listLdapAttribute;
	}
	
	/**
	 * Gets the atributo.
	 *
	 * @param atributo
	 *            the atributo
	 * @return the atributo
	 */
	public String getAttribute(String atributo) {
		String svalor = "";
		List<LdapAttribute> atributos = this.getListaLdapAttribute();
		if (!atributos.isEmpty()) {
			for (LdapAttribute item : atributos) {
				if (item.getId().equals(atributo)) {
					svalor = item.getValue();
					break;
				}
			}
		}
		return svalor;
	}
	
	/**
	 * Gets the matricula.
	 *
	 * @return the matricula
	 */
	public Long getMatricula() {
		String atributo = getAttribute("description");
		if (atributo!=null && !atributo.isEmpty()) {
			//atributo = getDescriptionEstagiario(atributo);
			return Long.parseLong(atributo);
		
		} else
			return 0L;
	}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin(){
		return getAttribute("name");		
	}
	
	/**
	 * Gets the nome.
	 *
	 * @return the nome
	 */
	public String getNome(){
		return getAttribute("displayname");		
	}
	
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail(){
		String atributo = getAttribute("mail");
		if (atributo!=null && !atributo.isEmpty())
			return atributo;
		else {
			atributo = getAttribute("userPrincipalName");
			if (atributo!=null && !atributo.isEmpty())
				return atributo;
			else
				return "";
		}
	}
	
	/**
	 * Gets the ldap DN.
	 *
	 * @return the ldap DN
	 */
	public String getLdapDN(){
		return getAttribute("distinguishedname");
	}
/*
	public boolean isCpf() {
		return cpf;
	}
*/	
}
