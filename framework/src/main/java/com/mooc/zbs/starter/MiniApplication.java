package com.mooc.zbs.starter;

import com.mooc.zbs.core.ClassScanner;
import com.mooc.zbs.web.headler.HandlerManager;
import com.mooc.zbs.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

public class MiniApplication {
    public static void run(Class<?> cls,String [] args){
        System.out.println("Hello Word MiniSpring");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            //测试获取所有路径的下的包名:
            try {
                List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
                //初始化所有的MappingHandler
                HandlerManager.resolveMappingHandler(classList);
                classList.forEach(it-> System.out.println(it.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
