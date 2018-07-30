package com.xinho.spring.framework.beans;

/**
 * @ClassName BeanPostProcessor
 * @Description 做事件监听
 * @Author 刘海飞
 * @Date 2018/7/30 18:04
 * @Version 1.0
 **/
public class BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }
    public Object postProcessAfterInitialization(Object bean, String beanName){
        return bean;
    }
}
