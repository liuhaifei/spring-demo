package com.xinho.spring.framework.context;

import com.xinho.spring.demo.action.MyAction;
import com.xinho.spring.framework.annotation.Autowired;
import com.xinho.spring.framework.annotation.Controller;
import com.xinho.spring.framework.annotation.Service;
import com.xinho.spring.framework.aop.AopConfig;
import com.xinho.spring.framework.beans.BeanDefinition;
import com.xinho.spring.framework.beans.BeanPostProcessor;
import com.xinho.spring.framework.beans.BeanWrapper;
import com.xinho.spring.framework.context.support.BeanDefinitionReader;
import com.xinho.spring.framework.core.BeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ApplicationContext
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/7/30 11:10
 * @Version 1.0
 **/
public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory{
    //配置文件
    private String[] configLocations;
    private BeanDefinitionReader reader;
    //用来保证注册式单例的容器
    private Map<String,Object> beanCacheMap=new HashMap<String,Object>();
    //用来存储所有被代理的对象
    private Map<String,BeanWrapper> beanWrapperMap=new HashMap<String,BeanWrapper>();

    public ApplicationContext(String... configLocations){
        this.configLocations=configLocations;
        refresh();
    }
    /**
     * @Description: spring 启动方法
     * @param []
     * @author lhf
     * @return
     * @date 2018/7/30 11:56
     */
    public void refresh(){

        //定位
        this.reader=new BeanDefinitionReader(configLocations);
        //加载
        List<String> beanDefinitions=reader.loadBeanDefinitions();
        //注册
        doRegistry(beanDefinitions);
        //依赖注入（lazy-init=false）自动调用getBean方法
        doAutowrited();

//        MyAction myAction = (MyAction)this.getBean("myAction");
//        myAction.query(null,null,"任性的Tom老师");

    }
    /**
     * @Description: 自动化依赖注入
     * @param []
     * @author lhf
     * @return
     * @date 2018/7/30 15:27
     */
    private void doAutowrited() {
        for (Map.Entry<String,BeanDefinition> beanDefinitionEntry:this.beanDefinitionMap.entrySet()){
            String beanName=beanDefinitionEntry.getKey();
            //如果lazy-init为false就自动注入
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                //修改逻辑 如果没有初始化则初始化
                if(!beanWrapperMap.containsKey(beanName)){
                    Object wrapperInstance= getBean(beanName);
                }

            }
        }
    }

    /**
     * @Description: 包装bean实例
     * @param [key, originalInstance]
     * @author lhf
     * @return
     * @date 2018/7/30 15:41
     */
    private void populateBean(String beanName, Object instance) {
        Class clazz= instance.getClass();
        if(!(clazz.isAnnotationPresent(Controller.class)
            ||clazz.isAnnotationPresent(Service.class))){
            return;
        }
       Field[] fields=clazz.getDeclaredFields();
        for (Field field:fields){
            if(!field.isAnnotationPresent(Autowired.class)){continue;}
            Autowired autowired=field.getAnnotation(Autowired.class);
            String autowiredBeanName=autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName=field.getType().getName();
            }

            //该bean有可能还未初始化,则初始化
            if(!this.beanWrapperMap.containsKey(autowiredBeanName)){
                getBean(autowiredBeanName);
            }

            field.setAccessible(true);

            try {
                this.beanWrapperMap.get(autowiredBeanName);
                field.set(instance,this.beanWrapperMap.get(autowiredBeanName).getWrapperInstance());
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description: 将beanDefinitons注册到IOC容器中
     * @param [beanDefinitions]
     * @author lhf
     * @return
     * @date 2018/7/30 15:04
     */
    private void doRegistry(List<String> beanDefinitions) {
        /**beanName有三种情况
         * 1.默认是类名首字母小写
         * 2.自定义名字
         * 3.接口注入
         * */
        try {
            for (String className:beanDefinitions){
                Class<?> beanClass=Class.forName(className);
                //如果是一个接口 ，是不能实例化得
                if(beanClass.isInterface()){continue;}
                BeanDefinition beanDefinition=reader.registerBean(className);
                if(beanDefinition!=null){
                    this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
                }
                //获取所有的接口
                Class<?>[] interfaces=beanClass.getInterfaces();
                for (Class<?> i:interfaces){
                    //如果是多个实现类，只能覆盖
                    this.beanDefinitionMap.put(i.getName(),beanDefinition);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 依赖注入，通过读取BeanDefinition中的信息
     * 然后通过反射机制创建一个实例
     * spring不会把原始的对象返回出去，会用一个BeanWrapper来进行一次包装
     * @param [beanName]
     * @author lhf
     * @return
     * @date 2018/7/30 17:58
     */
    @Override
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition=this.beanDefinitionMap.get(beanName);
        String className=beanDefinition.getBeanClassName();

        try {
            //生成通知事件
            BeanPostProcessor beanPostProcessor=new BeanPostProcessor();
            Object instance=instantionBean(beanDefinition);

            if (instance==null){
                return null;
            }
            //在实例初始化以前调用一次
            beanPostProcessor.postProcessBeforeInitialization(instance,beanName);

            BeanWrapper beanWrapper=new BeanWrapper(instance);
            beanWrapper.setPostProcessor(beanPostProcessor);
            beanWrapper.setAopConfig(instantionAopConfig(beanDefinition));
            this.beanWrapperMap.put(beanName,beanWrapper);

            //在实例初始化后调用一次
            beanPostProcessor.postProcessAfterInitialization(instance,beanName);

            //构建实例
            populateBean(beanName,instance);
            //返回包装类
            return this.beanWrapperMap.get(beanName).getWrapperInstance();
        }catch (Exception e){

        }
        return null;
    }

    private AopConfig instantionAopConfig(BeanDefinition beanDefinition) throws Exception {
        AopConfig aopConfig=new AopConfig();
        String expression =reader.getConfig().getProperty("pointCut");
        String[] before=reader.getConfig().getProperty("aspectBefore").split("\\s");
        String[] after=reader.getConfig().getProperty("aspectAfter").split("\\s");
        //获取类名
        String name=beanDefinition.getBeanClassName();
        //通过反射得到类
        Class<?> clazz= Class.forName(name);
        Pattern pattern=Pattern.compile(expression);

        Class aspectClass=Class.forName(before[0]);

        //在这里得到的方法都是原生的方法
        for (Method m:clazz.getMethods()){
            Matcher matcher=pattern.matcher(m.toString());
            if(matcher.matches()){
                //能满足切面规则的类，添加到aop切面中
                aopConfig.put(m,aspectClass.newInstance()
                        ,new Method[]{aspectClass.getMethod(before[1]),aspectClass.getMethod(after[1])});
            }
        }
        return aopConfig;
    }

    private Object instantionBean(BeanDefinition beanDefinition) {
        Object instance=null;
        String className=beanDefinition.getBeanClassName();
        try {
            //因为根据class才能确定一个类是否有实例
            if(this.beanCacheMap.containsKey(className)){
                instance=this.beanCacheMap.get(className);
            }else {
                Class<?> clazz=Class.forName(className);
                instance=clazz.newInstance();
                this.beanCacheMap.put(className,instance);
            }
            return instance;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return  this.beanDefinitionMap.size();
    }


    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
