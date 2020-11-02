package ru.javaops.masterjava.web;

import com.google.common.collect.ImmutableMap;
import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.javaops.masterjava.common.web.ThymeleafListener.engine;

@WebServlet(urlPatterns = "/")
public class UserServlet extends HttpServlet {
    private UserDao userDao = DBIProvider.getDao(UserDao.class);
    private static final int LIMIT_USER_VIEW = 20;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale(), ImmutableMap.of("users", userDao.getWithLimit(LIMIT_USER_VIEW)));
        engine.process("users", webContext, resp.getWriter());
    }
}
