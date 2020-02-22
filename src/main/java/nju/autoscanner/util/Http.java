package nju.autoscanner.util;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Http {
    //包括cookie在内的状态管理
    private CloseableHttpClient client;

    public Http() {
        CookieStore cookieStore = new BasicCookieStore();
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }

    public CloseableHttpResponse get(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        return client.execute(get);
    }

    public CloseableHttpResponse post(String url) throws IOException {
        HttpPost post=new HttpPost(url);
        return client.execute(post);
    }
}
