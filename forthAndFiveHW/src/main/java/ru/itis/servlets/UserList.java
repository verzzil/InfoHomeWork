package ru.itis.servlets;

import ru.itis.consts.ConnectDB;
import ru.itis.models.User;
import ru.itis.repositiories.UsersRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user-list")
public class UserList extends HttpServlet {

    List<User> users = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        try {
            UsersRepositoryImpl usersRepository = new UsersRepositoryImpl(
                    DriverManager.getConnection(ConnectDB.DB_URL, ConnectDB.DB_USER, ConnectDB.DB_PASSWORD)
            );

            users = usersRepository.findAll();
        }
        catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("usersList", users);
        req.getRequestDispatcher("/jsp/user-list.jsp").forward(req,resp);
    }
}