package com.mooc.zbs.web.headler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MappingHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;
    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
      String requestUri = ((HttpServletRequest) req).getRequestURI();
        if (!uri.equals(requestUri)) {
            return false;
        }
        Object[] parameters = new Object[args.length];
        //获取到所有的参数列表
        for (int i = 0; i <args.length ; i++) {
            parameters[i] = req.getParameter(args[i]);
        }
        //实例化参数构造方法
        Object ctl = controller.newInstance();
        //保存返回的结果
        Object response = method.invoke(ctl,parameters);
        res.getWriter().println(response.toString());
        return true;
    }
    MappingHandler(String uri,Method method,Class<?> cls,String [] args) {
        this.uri = uri;
        this.method = method;
        this.controller = cls;
        this.args = args;
    }
}
