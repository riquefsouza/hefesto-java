package br.com.hfs.dao;

import javax.ejb.Stateless;

import br.com.hfs.base.BaseDAO;
import br.com.hfs.model.AdmUserIp;
import br.com.hfs.model.AdmUserIpPK;

@Stateless
public class AdmUserIpDAO extends BaseDAO<AdmUserIp, AdmUserIpPK> {

	private static final long serialVersionUID = 1L;

	public AdmUserIpDAO() {
		super(AdmUserIp.class);
	}
	
}
