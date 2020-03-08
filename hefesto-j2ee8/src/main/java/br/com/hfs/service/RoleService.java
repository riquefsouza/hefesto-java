package br.com.hfs.service;

import br.com.hfs.dao.RoleDAO;
import br.com.hfs.model.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class RoleService {

    @Inject
    private RoleDAO roleDAO;

    public Optional<Role> findById(Role role){
        return roleDAO.findById(role);
    }

    public Optional<Role> findById(Long id){
        return roleDAO.findById(Role.build(id));
    }

    public List<Role> findAll(){
        return roleDAO.findAll();
    }

    public Role save(Role role){
		return roleDAO.save(role);
    }

    public Role update(Role role){
		return roleDAO.update(role);
    }
    
    public void remove(Role role){
		roleDAO.remove(role);
    }
}
