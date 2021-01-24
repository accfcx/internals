package com.accfcx;

import com.accfcx.tomcat.servlet.HttpSever1;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        HttpSever1 server = new HttpSever1();
        server.await();
    }
}
