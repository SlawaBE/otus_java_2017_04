package ru.otus.hw15.frontend.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw15.app.dataset.UserDataSet;
import ru.otus.hw15.db.messages.CreateUserRequest;
import ru.otus.hw15.db.messages.LoadUserRequest;
import ru.otus.hw15.messageSystem.*;
import ru.otus.hw15.messageSystem.messages.Response;
import ru.otus.hw15.messageSystem.messages.TextResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav on 28.06.2017
 */
public class DataBaseServlet extends AutowiredServlet implements Addressee {

    private static final String PAGE_TEMPLATE = "database.html";

    private static final Address address = new Address("dbServlet");

    @Autowired
    private MessageSystem messageSystem;

    public DataBaseServlet() {
        messageSystem.addAdressee(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqId = req.getParameter("id");
        String result;
        if (checkId(reqId)) {
            Long id = Long.parseLong(reqId);
            Response loadUserResponse;

            try {

                LoadUserRequest loadUserRequest = new LoadUserRequest(address, new Address("dbService"));
                loadUserRequest.setUserId(id);

                loadUserResponse = messageSystem.getResponse(messageSystem.sendMessage(loadUserRequest));

                if (loadUserResponse.getResult() != null) {
                    result = loadUserResponse.getResult().toString();
                } else {
                    result = "user not found";
                }

            } catch (Exception e) {
                result = "FAILED" + e.getMessage();
                e.printStackTrace();
            }

        } else {
            result = "Field 'id' must not be empty!";
        }
        generateResponse(resp, result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String result;
        if (checkInputParameters(name, age)) {
            TextResponse textResponse;
            try {

                CreateUserRequest createUserRequest = new CreateUserRequest(address, new Address("dbService"));
                createUserRequest.setUser(new UserDataSet(name, Integer.parseInt(age)));

                textResponse = (TextResponse) messageSystem.getResponse(messageSystem.sendMessage(createUserRequest));

                result = textResponse.getResult();

            } catch (Exception e) {
                result = "FAILED" + e.getMessage();
            }

        } else {
            result = "Fields 'name' and 'age' must not be empty!";
        }
        generateResponse(resp, result);
    }

    @Override
    public Address getAddress() {
        return address;
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
