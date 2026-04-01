package ru.rsreu.lab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.lab1.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final DataSource springDataSource;
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "user", "user")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");

            while (resultSet.next()) {

                long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");

                users.add(User.builder().id(id).login(login).name(name).role(role).build());
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return users;
    }


    @Override
    public List<Map<String, Object>> findSmthUsers(String sql) {
        List<Object> users = new ArrayList<>();
        List<Map<String, Object>> objects = new ArrayList<>();

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres", "user", "user")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(resultSet.getMetaData().getColumnLabel(i), resultSet.getObject(i));
                }
                objects.add(map);
//                long id = resultSet.getLong("id");
//                String login = resultSet.getString("login");
//                String name = resultSet.getString("name");
//                String role = resultSet.getString("role");
//
//                users.add(User.builder().id(id).login(login).name(name).role(role).build());
            }
        } catch (Exception e) {
//            return Collections.emptyList();
            return null;
        }
        return objects;
    }

    @Override
    public List<User> findUsers(String nameToSearch) {
        List<User> users = new ArrayList<>();

        try (Connection connection = springDataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from users where LOWER(name) like LOWER(?)");
            statement.setString(1, "%" + nameToSearch + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");

                users.add(User.builder().id(id).login(login).name(name).role(role).build());
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public int addUser(String login, String name, String role) throws SQLException {
        String sql = "call add_user(?, ?, ?, ?)";
        int id = -1;
        try (Connection conn = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres", "user", "user")) {
             CallableStatement stmt = conn.prepareCall(sql);

            // Установка параметров (индексация с 1)
            stmt.setString(1, login);
            stmt.setString(2, name);
            stmt.setString(3, role);
            stmt.registerOutParameter(4, Types.INTEGER);
            // Выполнение процедуры
            stmt.execute();
            id = stmt.getInt(4);
            System.out.println("Процедура успешно выполнена id пользователя:" + id);
            return id;
        } catch (SQLException e) {
            System.err.println("Ошибка при вызове процедуры: " + e.getMessage());
            e.printStackTrace();
            return id;
        }
    }

}
