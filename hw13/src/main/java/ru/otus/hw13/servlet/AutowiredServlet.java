package ru.otus.hw13.servlet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.http.HttpServlet;

/**
 * Created by Stanislav on 02.08.2017
 */
public abstract class AutowiredServlet extends HttpServlet {

    public AutowiredServlet() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

}
