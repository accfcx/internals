package com.accfcx.tomcat.servlet;

import java.io.IOException;

/**
 * @author accfcx
 * @desc
 */
public class StaticResourceProcessor {
    public void process(Request request, Response response){
        try{
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
