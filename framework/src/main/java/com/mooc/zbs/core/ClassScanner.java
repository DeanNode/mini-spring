package com.mooc.zbs.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        //用于储存文件的对应路径
        List<Class<?>> classList = new ArrayList<>();
        //得到对应包文件路径
        String path = packageName.replace(".", "/");
        //得到默认的类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //得到类加载器的可便利的资源
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            //判断资源类型是否是jar包形式的
            if (resource.getProtocol().contains("jar")) {
                //如果是对应jar类型的则直接使用jar形式强制转换成对应jar包形式的连接
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                //获取jar的路径名
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(jarFilePath, path));
            } else {

            }
        }
        return classList;
    }

    /**
     * 初始化容器用来存储一个类
     * 对应功能：实现类扫描器
     **/
    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();
            //找到路径相匹配或者以路径为开头的文件
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                //把jarEntry的class后缀去除，然后将结尾的class后缀删除掉即可
                String classFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
