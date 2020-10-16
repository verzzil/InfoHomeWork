package ru.itis.servlets;

import ru.itis.consts.ConnectDB;
import ru.itis.models.User;
import ru.itis.repositiories.UsersRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/sign-in")
public class SignIn extends HttpServlet {

    private UsersRepositoryImpl usersRepository;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        try {
            usersRepository = new UsersRepositoryImpl(
                    DriverManager.getConnection(ConnectDB.DB_URL, ConnectDB.DB_USER, ConnectDB.DB_PASSWORD)
            );
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/sign-in.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Optional<User> currentUser = usersRepository.findUserByLogin(login);

        if (currentUser.isPresent()) {
            if (
                    currentUser.get().getFirstName().equals(login) &&
                            currentUser.get().getPassword().equals(password)
            ) {
                Cookie cookie = new Cookie("Auth", currentUser.get().getUuid());
                resp.addCookie(cookie);
                resp.sendRedirect("/user-list");
            }
            else {
                resp.sendRedirect("/sign-in");
            }
        } else {
            resp.sendRedirect("/sign-in");
        }

    }
}
