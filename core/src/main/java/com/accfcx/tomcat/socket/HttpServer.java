package com.accfcx.tomcat.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author accfcx
 * @desc
 */
public class HttpServer {

    static final String ROOT  = System.getProperty("user.dir") + "/src/main/resources";

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        try {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            serverSocket = new ServerSocket(9091, 1, address);
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Socket socket;
        InputStream inputStream;
        OutputStream outputStream;
        try{
            System.out.println("server start and listen on 9091...");
            socket = serverSocket.accept();
            System.out.println("accept a socket connection:" + socket.getRemoteSocketAddress());
            inputStream  = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String uri = parseRequestAndGetUri(inputStream);
            buildResponse(outputStream, uri);

            Thread.sleep(10000);
            socket.close();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }


    }

    public String parseRequestAndGetUri(InputStream inputStream) {
        /*
         * GET /index.jsp HTTP/1.1
         * Host: 127.0.0.1
         */
        StringBuilder stringBuffer = new StringBuilder(2048);

        int i;

        byte[] buffer = new byte[2048];
        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }

        for (int j = 0; j < i; j++) {
            stringBuffer.append((char)buffer[j]);
        }

        System.out.println("request header:");
        System.out.println(stringBuffer.toString());

        return parseUri(stringBuffer.toString());

    }

    private String parseUri(String request) {
        int index1 = request.indexOf(' ');
        if (index1 != -1) {
            int index2 = request.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return request.substring(index1 + 1, index2);
            }
        }
        return null;
    }


    public void buildResponse(OutputStream outputStream, String uri) throws IOException {
        byte[] bytes = new byte[1024];
        FileInputStream fileInputStream = null;
        try{
            File file = new File(ROOT, uri);

            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                int ch = fileInputStream.read(bytes, 0, 1024);
                String header = "HTTP/1.1 200 OK\n" +
                        "\n";
                outputStream.write(header.getBytes());
                while (ch != -1) {
                    outputStream.write(bytes, 0, ch);
                    ch = fileInputStream.read(bytes, 0, 1024);
                }
            } else {
                String body = "<h1> File Not Found</h1>";
                String errorMsg = "HTTP/1.1 404 File Not Found\n"
                        + "Content-Type: text/html\n" +
                        "Content-Length: " + body.length() + "\n" +
                        "\n" +
                        body;
                outputStream.write(errorMsg.getBytes());
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }




    }

}
