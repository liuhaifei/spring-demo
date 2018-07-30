package com.xinho.spring.framework.context;

/**
 * @ClassName AbstractApplicationContext
 * @Description 抽象应用上下文 供子类重写
 * @Author 刘海飞
 * @Date 2018/7/30 11:05
 * @Version 1.0
 **/
public abstract class AbstractApplicationContext {

    protected void onRefresh(){
    }

    protected abstract void refreshBeanFactory();
}
