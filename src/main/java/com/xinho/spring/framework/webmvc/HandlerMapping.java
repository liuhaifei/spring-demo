package com.xinho.spring.framework.webmvc;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @ClassName HandlerMapping
 * @Description 用来保存请求方法与控制器的
 * @Author 刘海飞
 * @Date 2018/8/2 14:09
 * @Version 1.0
 **/
public class HandlerMapping {
    private Object controller;
    private Method method;
    private Pattern pattern;


    public HandlerMapping(Pattern pattern,Object controller,Method method){
        this.pattern=pattern;
        this.controller=controller;
        this.method=method;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
