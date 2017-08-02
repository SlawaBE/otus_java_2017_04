package ru.otus.hw13.service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav on 28.06.2017
 */
public class AuthorizationService {

    private Map<String, HttpSession> sessions = new HashMap<>();

    public void authorize(String id, HttpSession httpSession) {
        sessions.put(id, httpSession);
    }

    public HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public boolean isAuthorized(String id) {
        return sessions.containsKey(id);
    }

}
