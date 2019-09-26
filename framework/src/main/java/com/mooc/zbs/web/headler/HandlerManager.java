package com.mooc.zbs.web.headler;

import com.mooc.zbs.web.mvc.Controller;
import com.mooc.zbs.web.mvc.RequestMapping;
import com.mooc.zbs.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {
    public static List<MappingHandler> mappingHandlersList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList) {
        for(Class<?> cls:classList){
            if (cls.isAnnotationPresent(Controller.class)) {
                parseHandlerFromController(cls);
            }
        }
    }
    private static void parseHandlerFromController(Class<?> cls) {
        //使用反射获取到所有的方法
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            //如果没有打上RequestMapping标签的注解就不进行反射处理
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            //拿到uri路径
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> paramNameList = new ArrayList<>();
            //找到所有的请求参数当中存在RequestParam标签的参数
            for (Parameter parameter : method.getParameters()) {
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            //获取到所有的参数
            String [] params = paramNameList.toArray(new String[paramNameList.size()]);
            MappingHandler mappingHandler = new MappingHandler(uri,method,cls,params);
            //把构造好的Handler放到Handler管理器当中
            HandlerManager.mappingHandlersList.add(mappingHandler);

        }
    }

}
