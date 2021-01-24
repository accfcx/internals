package com.accfcx.tomcat.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * @author accfcx
 * @desc Server
 */
public class BaseServerSocket {
    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        ServerSocket socket = new ServerSocket(8080, 1, address);


    }
}
