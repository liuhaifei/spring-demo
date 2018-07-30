package com.xinho.spring.framework.core;

/**
 * @ClassName BeanFactory
 * @Description 该类提供了一个方法 根据benaName从IOC容器中获取一个bean实例
 * @Author 刘海飞
 * @Date 2018/7/30 11:11
 * @Version 1.0
 **/
public interface BeanFactory {
    /**
     * @Description: 根据beanName获取bean
     * @param [beanName]
     * @author lhf
     * @return
     * @date 2018/7/30 11:26
     */
    Object getBean(String beanName);
}
