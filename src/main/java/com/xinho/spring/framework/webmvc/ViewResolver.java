package com.xinho.spring.framework.webmvc;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ViewResolver
 * @Description 1.将静态文件变成一个动态文件 2.根据用户传递的参数不同，产生不同的结果
 * @Author 刘海飞
 * @Date 2018/8/2 14:11
 * @Version 1.0
 **/
public class ViewResolver {

    private String viewName;
    private File templateFile;

    public ViewResolver(String viewName,File templateFile){
        this.viewName=viewName;
        this.templateFile=templateFile;
    }

    public String viewResolver(ModelAndView mv) throws  Exception{
        StringBuilder sb=new StringBuilder();

        RandomAccessFile ra=new RandomAccessFile(this.templateFile,"r");
        
        try {
            String line=null;
            while (null!=(line=ra.readLine())){
                line=new String(line.getBytes("ISO-8859-1"),"utf-8");
                Matcher m=matcher(line);
                while (m.find()){
                    for(int i=1;i<=m.groupCount();i++){
                        //把￥{} 中间的字符串取出来
                        String paramName=m.group(i);
                        Object paramValue=mv.getModel().get(paramName);
                        if(paramValue==null){
                            continue;
                        }
                        line=line.replaceAll("￥\\{"+paramName+"\\}",paramValue.toString());
                        line=new String(line.getBytes("utf-8"),"ISO-8859-1");
                    }
                }
                sb.append(line);
            }
        }finally {
            ra.close();
        }
        return sb.toString();
    }

    private Matcher matcher(String line) {
        Pattern pattern=Pattern.compile("￥\\{(.+?)\\}",Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(line);
        return matcher;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
