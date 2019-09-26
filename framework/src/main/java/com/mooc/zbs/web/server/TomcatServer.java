package com.mooc.zbs.web.server;

import com.mooc.zbs.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;
    public TomcatServer(String[] args){
        this.args = args;
    }
    //启动tomcat的主方法
    public void  startServer() throws LifecycleException {
        tomcat = new Tomcat();
        //设置固定的端口
        tomcat.setPort(6699);
        //启动tomcat
        tomcat.start();
        /*Context是最接近servlet的容器
        这里是标准的实现了Context容器的初始化加载
        */
        Context context = new StandardContext();
        context.setPath("");
        //设置tomcat的声明周期声明对应的一个监听器
        context.addLifecycleListener(new Tomcat.FixContextListener());
        DispatcherServlet servlet = new DispatcherServlet();
        //追加对应的servlet实例
        //设置支持异步
        Tomcat.addServlet(context,"dispatcherServlet",servlet).setAsyncSupported(true);
        //设置对应的ur映射
        context.addServletMappingDecoded("/","dispatcherServlet");
        //将host容器传入context容器 建立tomcat和serlet之间的联系
        tomcat.getHost().addChild(context);
        //为了防止线程中断声明线程一直处于等待状态
        Thread awaitThread = new Thread("tomcat_await_thread"){
            @Override
            public void run(){
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        //设置线程为非守护线程
        awaitThread.setDaemon(false);
        awaitThread.start();

    }
}
