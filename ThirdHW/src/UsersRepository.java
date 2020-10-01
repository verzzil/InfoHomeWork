import java.util.List;

public interface UsersRepository extends CrudRepository<User> {
    List<User> findAllByAge(Integer age);
}
