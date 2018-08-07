package com.xinho.spring.framework.beans;

import com.xinho.spring.framework.aop.AopConfig;
import com.xinho.spring.framework.aop.AopProxy;
import com.xinho.spring.framework.core.FactoryBean;

/**
 * @ClassName BeanWrapper
 * @Description bean实例代理类
 * @Author 刘海飞
 * @Date 2018/7/30 13:48
 * @Version 1.0
 **/
public class BeanWrapper extends FactoryBean{
    /**包装的实例*/
    private Object wrapperInstance;
    /**原始的实例*/
    private Object originalInstance;

    private AopProxy aopProxy=new AopProxy();

    //还会用到  观察者  模式
    //1、支持事件响应，会有一个监听
    private BeanPostProcessor postProcessor;

    public BeanWrapper(Object instance){
        this.wrapperInstance=aopProxy.getProxy(instance);
        this.originalInstance=instance;
    }

    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    public Object getOriginalInstance() {
        return originalInstance;
    }

    //返回代理的class
    public Class<?> getWrappedClass(){
        return this.wrapperInstance.getClass();
    }
    public BeanPostProcessor getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(BeanPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    public void setAopConfig(AopConfig aopConfig) {
        aopProxy.setConfig(aopConfig);
    }
}
