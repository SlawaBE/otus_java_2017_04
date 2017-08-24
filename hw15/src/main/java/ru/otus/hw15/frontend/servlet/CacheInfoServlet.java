package ru.otus.hw15.frontend.servlet;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw15.db.messages.CacheInfoRequest;
import ru.otus.hw15.db.messages.CacheInfoResponse;
import ru.otus.hw15.app.dto.CacheInfo;
import ru.otus.hw15.messageSystem.*;
import ru.otus.hw15.frontend.auth.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Stanislav on 20.08.2017
 */
public class CacheInfoServlet extends AutowiredServlet implements Addressee{

    private final Address address = new Address("cacheInfo");

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private MessageSystem messageSystem;

    public CacheInfoServlet() {
        messageSystem.addAdressee(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        String login = (String) req.getSession().getAttribute("login");
        if (authorizationService.isAuthorized(sessionId) && "admin".equals(login)) {
            CacheInfoRequest cacheInfoRequest = new CacheInfoRequest(address, new Address("dbService"));
            CacheInfoResponse cacheInfoResponse;

            try {

                cacheInfoResponse = (CacheInfoResponse) messageSystem.getResponse(messageSystem.sendMessage(cacheInfoRequest));
                CacheInfo info = cacheInfoResponse.getResult();

                resp.getWriter().print(new Gson().toJson(info));
                resp.setContentType("application/json;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (TimeoutException e) {
                resp.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
                e.printStackTrace();
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }

        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }

    @Override
    public Address getAddress() {
        return address;
    }
}
