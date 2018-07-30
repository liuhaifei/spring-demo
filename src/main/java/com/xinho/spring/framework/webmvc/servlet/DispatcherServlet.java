package com.xinho.spring.framework.webmvc.servlet;

import com.xinho.spring.framework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@ClassName DispatcherServlet
 *@Description 相当于是MVC的入口
 *@Author 刘海飞
 *@Date 2018/7/30 10:52
 *@Version 1.0
 **/
public class DispatcherServlet extends HttpServlet {
    //web.xml中定义的参数名
    private final String LOCATION="contextConfigLocation";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 启动IOC容器
        ApplicationContext context=new ApplicationContext();


    }
}
