package ru.itis.repositiories;

import ru.itis.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersRepositoryImpl implements UsersRepository {

    private Connection connection;

    //language=SQL
    private static final String SQL_SELECT_ALL_FROM_USER = "select * from users";
    //language=SQL
    private static final String SQL_SELECT_USER_BY_AGE = "select * from users where age = ";
    //language=SQL
    private static final String SQL_SAVE_USER = "insert into users (firstname, lastname, password, age, uuid) values ";


    public UsersRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_USER_BY_AGE + age);

            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getInt("age")
                );

                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from users where firstname = '" + login + "'");
            if(!result.next())
                return Optional.empty();
            User newUser = new User(
                    result.getString("firstname"),
                    result.getString("password"),
                    result.getString("uuid")
            );
            return Optional.of(newUser);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_USER);

            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getInt("age")
                );

                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(User entity) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                    SQL_SAVE_USER +
                            "('" +
                            entity.getFirstName() + "','" +
                            entity.getLastName() + "','" +
                            entity.getPassword() + "','" +
                            entity.getAge() + "','" +
                            UUID.randomUUID().toString() +
                            "')"
            );
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void remove(User entity) {

    }

    @Override
    public void removeById(User entity) {

    }
}
