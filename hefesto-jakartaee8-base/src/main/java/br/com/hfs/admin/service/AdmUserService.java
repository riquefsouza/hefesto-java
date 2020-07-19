package br.com.hfs.admin.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.TransactionException;
import org.hibernate.jdbc.ReturningWork;
import org.modelmapper.ModelMapper;
import org.omnifaces.util.Faces;

import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.repository.AdmUserRepository;
import br.com.hfs.admin.vo.UserVO;
import br.com.hfs.base.BaseService;

public class AdmUserService extends BaseService<AdmUser, Long, AdmUserRepository> {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	protected EntityManager em;

	public List<AdmUser> findPaginated(int pageNumber, int pageSize) {
		return repository.findPaginated(pageNumber, pageSize);
	}

	public List<AdmUser> listByRange(int startInterval, int endInterval) {
		return repository.listByRange(startInterval, endInterval);
	}
	
	private List<UserVO> toVO(List<AdmUser> listaOrigem) {
		ModelMapper modelMapper = new ModelMapper();		
		UserVO vo; 
		List<UserVO> lista = new ArrayList<UserVO>(); 
		for (AdmUser item : listaOrigem) {
			vo = modelMapper.map(item, UserVO.class);
			lista.add(vo);
		}
		return lista;
	}
	
	public Optional<AdmUser> findByLogin(String login) {
		return repository.findByLogin(login);
	}

	public List<UserVO> findByLikeEmail(String email){
		List<AdmUser> listObj = repository.findByLikeEmail(email);
		return toVO(listObj);
	}
	
	public String findIPByOracle() {
		List<Object> ip = repository.findIPByOracle();
		return ip.get(0).toString();
	}
	
	public String findIPByPostgreSQL() {
		List<Object> ip = repository.findIPByPostgreSQL();
		return ip.get(0).toString();
	}
	
	public String setLoginPostgreSQL(String login) {
		List<Object> lista = repository.setLoginPostgreSQL(login);
		return lista.get(0).toString();
	}
	
	public String setIPPostgreSQL(String ip) {
		List<Object> lista = repository.setIPPostgreSQL(ip);
		return lista.get(0).toString();
	}
	
	public String findBanco() throws TransactionException {
		String retorno = "";
		final SQLException[] erro = new SQLException[1];

		if (em.isOpen()) {

			Session session = em.unwrap(Session.class);

			retorno = session.doReturningWork(new ReturningWork<String>() {
				@Override
				public String execute(Connection connection) {
					try {
						if (!connection.isClosed()) {
							DatabaseMetaData dbmd = connection.getMetaData();

							if (dbmd.getDriverName().indexOf("Oracle") != -1) {
								return "Oracle";
							}
							if (dbmd.getDriverName().indexOf("PostgreSQL") != -1) {
								return "PostgreSQL";
							}
							if (dbmd.getDriverName().indexOf("mysql") != -1) {
								return "MySQL";
							}
						}
					} catch (SQLException e) {
						erro[0] = e;
						return e.getMessage();
					}
					return "";
				}
			});

		}

		if (erro.length > 0) {
			if (erro[0] != null) {
				if (!erro[0].getMessage().isEmpty()) {
					throw new TransactionException(erro[0].getMessage(), erro[0]);
				}
			}
		}

		return retorno;
	}
	
	public boolean setOracleLoginAndIP(String login, String ip) throws TransactionException {
		Integer retorno = -1;
		final SQLException[] erro = new SQLException[1];

		if (em.isOpen()) {

			Session session = em.unwrap(Session.class);

			retorno = session.doReturningWork(new ReturningWork<Integer>() {
				@Override
				public Integer execute(Connection connection) {
					try {
						if (!connection.isClosed()) {
							CallableStatement call = connection.prepareCall("{ call pkg_adm.setar_usuario_ip(?, ?) }");
							call.setString(1, login);
							call.setString(2, ip);
							call.executeUpdate();
						}
					} catch (SQLException e) {
						erro[0] = e;
						return -1;
					}
					return 0;
				}
			});

		}

		if (erro.length > 0) {
			if (erro[0] != null) {
				if (!erro[0].getMessage().isEmpty()) {
					throw new TransactionException(erro[0].getMessage(), erro[0]);
				}
			}
		}

		return (retorno == 0);
	}
	
	@Transactional
	public AdmUser getUser(Long id, String login, String name, String email, String ldapDN, boolean auditar) throws TransactionException {
		AdmUser user;
		Optional<AdmUser> ouser = this.findById(id);
		if (ouser.isEmpty()) {
			user = new AdmUser();
			user.setId(null);
			user.setLogin(login);
			user.setName(name);
			user.setEmail(email);
			user.setIp(Faces.getRemoteAddr()); //OmniFaces
			user.setLdapDN(ldapDN);
			
			if (auditar) {
				this.insert(user);
			}
		} else {
			user = ouser.get();
		}
		
		if (auditar) {
			String banco = findBanco();
			if (banco.equals("Oracle")) {
				setOracleLoginAndIP(user.getLogin().toString(), user.getIp());
			}
			if (banco.equals("PostgreSQL")) {
				setLoginPostgreSQL(user.getLogin().toString());
				setIPPostgreSQL(user.getIp());
			}
		}

		return user;
	}

}
