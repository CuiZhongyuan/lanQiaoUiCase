package com.iwebui.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GetOcrUtil {
    public static String getPath(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String path = "D:\\picture\\"+System.currentTimeMillis()+".png";
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                InputStream inStream = entity.getContent();
                FileOutputStream fw = new FileOutputStream(path, false);
                int b = inStream.read();
                while (b != -1) {
                    fw.write(b);
                    b = inStream.read();
                }
                fw.close();
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
        return path;
    }
}
