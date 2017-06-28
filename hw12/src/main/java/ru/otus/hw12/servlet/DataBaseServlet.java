package ru.otus.hw12.servlet;

import ru.otus.hw12.dataset.UserDataSet;
import ru.otus.hw12.service.DBService;

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
public class DataBaseServlet extends HttpServlet {

    private static final String PAGE_TEMPLATE = "database.html";
    DBService dbService;

    public DataBaseServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqId = req.getParameter("id");
        String result;
        if (checkId(reqId)) {
            Long id = Long.parseLong(req.getParameter("id"));
            UserDataSet user = dbService.load(id);
            if (user != null) {
                result = user.toString();
            } else {
                result = "user not found";
            }
        } else {
            result = "";
        }
        generateResponse(resp, result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String result;
        if (checkInputParameters(name, age)) {
            try {
                dbService.save(new UserDataSet(name, Integer.parseInt(age)));
                result = "SUCCESS";
            } catch (Exception e) {
                result = "FAILED";
            }
        } else {
            result = "Fields 'name' and 'age' must not be null!";
        }
        generateResponse(resp, result);
    }

    private void generateResponse(HttpServletResponse resp, String result) throws IOException {
        String page = getPage(result);
        resp.getWriter().println(page);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private boolean checkId(String reqId) {
        return reqId != null && !reqId.equals("");
    }

    private boolean checkInputParameters(String name, String age) {
        return name != null && !name.equals("") && age != null && !age.equals("");
    }

    private String getPage(String result) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("result", result);
        return TemplateProcessor.instance().getPage(PAGE_TEMPLATE, pageVariables);
    }

}
