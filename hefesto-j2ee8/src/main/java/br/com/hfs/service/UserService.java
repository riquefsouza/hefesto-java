package br.com.hfs.service;

import br.com.hfs.dao.UserDAO;
import br.com.hfs.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserService {

    @Inject
    private UserDAO userDAO;

    public Optional<User> findById(User user){
        return userDAO.findById(user);
    }

    public Optional<User> findById(Long id){
        return userDAO.findById(User.build(id));
    }

    public List<User> findAll(){
        return userDAO.findAll();
    }

    public User save(User user){
		return userDAO.save(user);
    }

    public User update(User user){
		return userDAO.update(user);
    }

    public void remove(User user){
		userDAO.remove(user);
    }
}
