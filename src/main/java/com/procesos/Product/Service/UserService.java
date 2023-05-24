package com.procesos.Product.Service;

import com.procesos.Product.Models.User;

import java.util.List;


public interface UserService {
    User getUser(Long id);
    Boolean createUser(User user);
    List<User> allUsers();
    Boolean UpdateUser(Long id, User user);
    String login(User user);
}