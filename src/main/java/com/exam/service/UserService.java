package com.exam.service;

import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {

    //Creating User
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    //Get user by userName
    public User getUser(String userName);

    //Delete user by userName
    public void deleteUser(Long userId);

}
