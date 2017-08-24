package ru.otus.hw15.frontend.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw15.frontend.auth.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav on 28.06.2017
 */
public class LoginServlet extends AutowiredServlet {

    private static final String LOGIN_VARIABLE_NAME = "login";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    @Autowired
    private AuthorizationService authorizationService;

    public LoginServlet(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public LoginServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (authorizationService.isAuthorized(session.getId())) {
            generateResponse(resp, (String) session.getAttribute("login"));
        } else {
            doPost(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        if (login != null) {
            String sessionId = req.getSession().getId();
            saveToSession(req, login);
            authorizationService.authorize(sessionId, req.getSession());
            saveToServlet(req, login);
            saveToCookie(resp, login);
        }

        generateResponse(resp, login);
    }

    private void generateResponse(HttpServletResponse resp, String login) throws IOException {
        String page = getPage(login);
        resp.getWriter().println(page);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void saveToCookie(HttpServletResponse response, String requestLogin) {
        response.addCookie(new Cookie("login", requestLogin));
    }

    private void saveToServlet(HttpServletRequest request, String requestLogin) {
        request.getServletContext().setAttribute("login", requestLogin);
    }

    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }

    private static String getPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }
}
