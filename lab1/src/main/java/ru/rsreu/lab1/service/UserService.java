package ru.rsreu.lab1.service;

import ru.rsreu.lab1.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUsers();
    List<User> findUsers(String name);
    List<Map<String, Object>> findSmthUsers(String sql);
    int addUser(String login, String name, String role) throws SQLException;
}
