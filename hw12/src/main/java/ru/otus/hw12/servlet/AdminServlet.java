package ru.otus.hw12.servlet;

import ru.otus.hw12.cache.CacheEngine;
import ru.otus.hw12.service.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav on 28.06.2017
 */
public class AdminServlet extends HttpServlet {

    private final String ADMIN_PAGE_TEMPLATE = "admin.html";

    private CacheEngine cache;

    private AuthorizationService authorizationService;

    public AdminServlet(CacheEngine cache, AuthorizationService authorizationService) {
        this.cache = cache;
        this.authorizationService = authorizationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        String login = (String) req.getSession().getAttribute("login");
        if (authorizationService.isAuthorized(sessionId) && "admin".equals(login)) {
            Map<String, Object> pageVariables = createPageVariablesMap(req);
            resp.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        String sessionId = request.getSession().getId();
        pageVariables.put("login", request.getSession().getAttribute("login"));
        pageVariables.put("cache_size", cache.size());
        pageVariables.put("cache_hit", cache.getHit());
        pageVariables.put("cache_miss", cache.getMiss());

        return pageVariables;
    }
}
