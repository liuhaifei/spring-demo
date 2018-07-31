package com.xinho.spring.framework.context.support;

import com.xinho.spring.framework.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName BeanDefinitionReader
 * @Description 配置文件解析器
 * @Author 刘海飞
 * @Date 2018/7/30 11:05
 * @Version 1.0
 **/
public class BeanDefinitionReader {

    private Properties config=new Properties();

    private List<String> registryBeanClasses=new ArrayList<String>();
    //在配置文件中，用来获取自动扫描的包名的key
    private final String SCAN_PACKAGE="scanPackage";

    public BeanDefinitionReader(String... locations){
        //通过reader去读取配置文件
        InputStream is=this.getClass()
                           .getClassLoader()
                           .getResourceAsStream(locations[0].replace("classpath:",""));

        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    doScanner(config.getProperty(SCAN_PACKAGE));
    }

    /**
     * @Description: 递归扫描所有的扫描路径下的类 ，并且保存到一个list中
     * @param [property]
     * @author lhf
     * @return
     * @date 2018/7/30 14:15
     */
    private void doScanner(String packageName) {
        URL url=this.getClass().getClassLoader()
                .getResource("/"+packageName.replaceAll("\\.","/"));
        File classDir=new File(url.getFile().replaceAll("%20"," "));

        for (File file:classDir.listFiles()){
            if(file.isDirectory()) {
                doScanner(packageName + "." + file.getName());
            }else{
                registryBeanClasses.add(packageName+"."+file.getName().replace(".class",""));
            }
        }
    }
    /**
     * @Description: 每注册一个className，就返回一个beanDefinition 对配置信息进行一个包装
     * @param [className]
     * @author lhf
     * @return
     * @date 2018/7/30 14:49
     */
    public BeanDefinition registerBean(String className){
        if(this.registryBeanClasses.contains(className)){
            BeanDefinition beanDefinition=new BeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(lowerFirstCase(className.substring(className.lastIndexOf(".")+1)));
            return beanDefinition;
        }
        return null;
    }

    /**
     * @Description: 首字母小写
     * @param [str]
     * @author lhf
     * @return
     * @date 2018/7/30 14:18
     */
    public String lowerFirstCase(String str){
        char[] chars=str.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }
    public List<String> loadBeanDefinitions(){
        return this.registryBeanClasses;
    }

    public Properties getConfig() {
        return config;
    }

    public List<String> getRegistryBeanClasses() {
        return registryBeanClasses;
    }
}
