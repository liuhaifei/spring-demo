package com.xinho.spring.framework.beans;

/**
 * @ClassName BeanDefinition
 * @Description 用来存储配置信息 相当于保存在内存中的配置
 * @Author 刘海飞
 * @Date 2018/7/30 13:46
 * @Version 1.0
 **/
public class BeanDefinition {

    private String beanClassName;
    private boolean lazyInit=false;

    private String factoryBeanName;


    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
