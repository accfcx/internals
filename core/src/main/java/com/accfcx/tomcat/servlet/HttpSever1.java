package com.accfcx.tomcat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author accfcx
 * @desc
 */
public class HttpSever1 {
    public static void main(String[] args) {
        HttpSever1 server = new HttpSever1();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 9091;
        try{
            serverSocket = new ServerSocket(port,
                    1,
                    InetAddress.getByName("127.0.0.1")
            );

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            Socket socket;
            InputStream inputStream;
            OutputStream outputStream;

            try{
                socket = serverSocket.accept();

                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                Request request = new Request(inputStream);
                request.parse();

                Response response = new Response(outputStream);
                response.setRequest(request);

                if (request.getUri().startsWith("/servlet")) {
                    ServletContainer1 container = new ServletContainer1();
                    container.process(request, response);
                } else {
                    StaticResourceProcessor processor= new StaticResourceProcessor();
                    processor.process(request, response);
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
