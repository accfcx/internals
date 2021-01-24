package com.accfcx.tomcat.socket;

import java.io.*;
import java.net.Socket;

/**
 * @author accfcx
 * @desc Client
 */
public class ClientSocket {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 9091);

        OutputStream os = socket.getOutputStream();
        PrintWriter out = new
                PrintWriter(os, true);

        out.println("GET /index.html HTTP/1.1");
        out.println("Host: 127.0.0.1:9091");
        out.println();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread.sleep(1000);
        boolean over = false;
        StringBuilder stringBuffer = new StringBuilder();
        while (!over) {
            while (reader.ready()) {
                int i = reader.read();
                stringBuffer.append((char)i);

            }
            over = true;
        }


        System.out.println(stringBuffer.toString());
        socket.close();
    }
}
