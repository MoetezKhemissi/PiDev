package com.example.Project.Interfaces;

import com.example.Project.entities.User;

import java.util.List;

public interface IUserService {

    List<User> retrieveAllUsers();

    User addUser(User user);

    User updateUser (User user);

    User retrieveUser (Long IdUser);

    void deleteUser( Long IdUser);

    User retrieveUserByUsername(String Username);

    public User registerUser(User user);

    User retrieveUserByPhone(Long Phone);



}
