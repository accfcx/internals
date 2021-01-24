package com.accfcx.tomcat.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @author accfcx
 * @desc
 *
 * class loader
 *
 */
public class ServletContainer1 {

    public void process(Request request, Response response) {

        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);

        URLClassLoader classLoader = null;
        try{
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.ROOT);
            String path = classPath.getCanonicalPath();


            String repository = (new URL("file", null,
             path + File.separator)).toString();

            urls[0] = new URL(null, repository, streamHandler);

            classLoader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class myClass = null;
        try{
            myClass = classLoader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet servlet = null;
        try{
            servlet = (Servlet) myClass.getDeclaredConstructor().newInstance();
            servlet.service( request, response);
        } catch (Throwable e){
            e.printStackTrace();
        }


    }
}
