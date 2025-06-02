package com.mochi.demo.java11;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Java11HttpClientDemo {

    public static void main(String[] args) {
        try {
            send1();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void send1() throws URISyntaxException, IOException, InterruptedException {
        // 直接创建一个新的HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // 构造一个Http请求实体 就可以让客户端帮我们发送出去了
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI("https://baidu.com")).build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

}
