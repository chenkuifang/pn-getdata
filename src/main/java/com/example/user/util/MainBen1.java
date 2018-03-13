package com.example.user.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MainBen1 {

    public static void main(String[] args) throws ClientProtocolException, IOException {
//        String url="http://www.yaopinnet.com/z31/Z23020431.htm";
//        CloseableHttpClient client = HttpClients.createDefault();
//        //设置代理
//        HttpHost proxy = new HttpHost("120.85.100.206", 808, "http");
//        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//        HttpGet get=new HttpGet(url);
//
//        //模拟浏览器
//        get.setConfig(config);
//        get.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        get.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//        get.setHeader("Accept-Encoding", "gzip, deflate");
//        get.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
//        get.setHeader("Connection", "keep-alive");
//        get.setHeader("Host", "www.cnvd.org.cn");
//        get.setHeader("referer", "http://www.cnvd.org.cn/");
//        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
//        CloseableHttpResponse response = client.execute(get);
//
//        HttpEntity entity = response.getEntity();
//        String html= EntityUtils.toString(entity,"utf-8");
//        System.out.println(html);


        //创建客户端
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //创建请求Get实例
        HttpGet httpGet = new HttpGet("http://www.jobuy.com/jobs?sltkeyword=Z44023447");

        //设置头部信息进行模拟登录（添加登录后的Cookie）
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml," +
                "application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        //httpGet.setHeader("Cookie", ".......");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);

            //得到服务响应状态码
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                //打印所有响应头
//                Header[] headers = closeableHttpResponse.getAllHeaders();
//                for (Header header : headers) {
//                    System.err.println(header.getName() + ": " + header.getValue());
//                }

                HttpEntity entity =  closeableHttpResponse.getEntity();
                String html= EntityUtils.toString(entity,"utf-8");
                System.err.println(html);

            } else {
                //如果是其他状态码则做其他处理
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //httpClient.close();
        }


    }
}
