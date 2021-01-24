package com.accfcx.tomcat.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * @author accfcx
 * @desc
 *
 * bug: Error: Parse Error: Expected HTTP/
 * HTTP需要添加响应头部
 */
public class Response implements ServletResponse {
    OutputStream outputStream;
    Request request;
    PrintWriter printWriter;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[1024];
        FileInputStream fileInputStream = null;
        try{
            File file = new File(Constants.ROOT, request.getUri());
            fileInputStream = new FileInputStream(file);
            int ch = fileInputStream.read(bytes, 0, 1024);

            String header = "HTTP/1.1 200 OK\n" +
                    "\n";
            outputStream.write(header.getBytes());
            while (ch != -1) {
                outputStream.write(bytes, 0, ch);
                ch = fileInputStream.read(bytes, 0, 1024);
            }
        } catch (FileNotFoundException e1) {
            String respBody = "<H1>File Not Found: " + request.getUri().substring(1);
            String resp = "HTTP/1.1 404 File Not Found\n" +
                    "Content-Type: text/html\n" +
                    "Content-Length: " + respBody.length() + "\n" +
                    "\n" + 
                    respBody;
            outputStream.write(resp.getBytes());
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        String header = "HTTP/1.1 200 OK\n" +
                "\n";
        outputStream.write(header.getBytes());
        printWriter = new PrintWriter(outputStream, true);
        return printWriter;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
