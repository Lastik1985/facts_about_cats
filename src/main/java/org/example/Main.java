package org.example;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String url = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        List<Cats> CatsList = getContent(url);
        CatsList.stream().filter(value -> value.getUpvotes() != null && Integer.parseInt(value.getUpvotes()) > 0)
                .forEach(System.out::println);
    }

    static List<Cats> getContent(String url) {
        CloseableHttpResponse response;
        ObjectMapper mapper = new ObjectMapper();
        List<Cats> list = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build()) {
            HttpGet request = new HttpGet(url);
            response = httpClient.execute(request);
            list = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
            });
            response.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;

    }
}