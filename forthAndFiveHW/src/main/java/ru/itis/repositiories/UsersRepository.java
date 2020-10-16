package ru.itis.repositiories;

import ru.itis.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    List<User> findAllByAge(Integer age);
    Optional<User> findUserByLogin(String login);
}
