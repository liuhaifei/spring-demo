package com.xinho.spring.framework.context;

import com.xinho.spring.framework.beans.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DefaultListableBeanFactory
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/7/30 15:13
 * @Version 1.0
 **/
public class DefaultListableBeanFactory extends AbstractApplicationContext {
    //用来保存配置信息
    protected Map<String,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<String, BeanDefinition>();

    @Override
    protected void refreshBeanFactory() {

    }
}
