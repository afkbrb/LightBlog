package com.iustu.service;

import com.iustu.entity.User;

public interface UserService {
    User getUserByUsername(String username);
    User getUserById(int id);
    void updateUser(User user);
}
