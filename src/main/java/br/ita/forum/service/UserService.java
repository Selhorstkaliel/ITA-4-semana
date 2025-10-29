package br.ita.forum.service;

import br.ita.forum.dao.UserDAO;
import br.ita.forum.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    public void registerUser(User user) throws SQLException {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("Login is required");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        
        User existing = userDAO.findByLogin(user.getLogin());
        if (existing != null) {
            throw new IllegalArgumentException("Login already exists");
        }
        
        userDAO.insert(user);
    }
    
    public User authenticate(String login, String password) throws SQLException {
        if (login == null || login.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        
        User user = userDAO.findByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public List<User> getRanking() throws SQLException {
        return userDAO.findAllOrderedByPoints();
    }
    
    public User getUserById(int id) throws SQLException {
        return userDAO.findById(id);
    }
}
