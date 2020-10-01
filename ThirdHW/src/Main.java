import java.sql.*;

public class Main {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "your pass";
    // Моя таблица назвается users , а не drivers
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";


    public static void main(String[] args) throws SQLException {
        UsersRepositpryImpl usersRepository = new UsersRepositpryImpl(
                DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        );

        for(User user : usersRepository.findAllByAge(19)) {
            System.out.println(
                    user.getId()+" "+
                    user.getFirstName()+" "+
                    user.getLastName()+" "+
                    user.getAge());
        }

    }
}
