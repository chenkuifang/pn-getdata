package com.example.user.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用药指导 网  http://yp.120ask.com/detail/51548.html
 */
public class MainBen1 {

    public static void main(String[] args) {
        String uri = "http://yp.120ask.com/search?kw=HC20090030";
        try {
            String detailUri = getDetailUri(uri);

            if (StringUtils.isNotBlank(detailUri)) {
                detailUri = "http:" + detailUri;
                getDetai(detailUri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getDetailUri(String uri) {

        String detailUri = "";

        //创建客户端
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //创建请求Get实例
        HttpGet httpGet = new HttpGet(uri);

        //设置头部信息进行模拟登录（添加登录后的Cookie）
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        //httpGet.setHeader("Cookie", ".......");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);

            //得到服务响应状态码
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = closeableHttpResponse.getEntity();
                String html = EntityUtils.toString(entity, "utf-8");
                //System.err.println(html);

                Document document = Jsoup.parse(html);
                Elements elements = document.getElementsByClass("s_result_content").select("ul").get(0).select("li");
                Elements dt = elements.select("dt");

                if (!dt.isEmpty()) {
                    detailUri = dt.get(0).getElementsByTag("a").attr("href");
                }
                System.err.println("http:" + detailUri);

            } else {
                //如果是其他状态码则做其他处理
                System.err.println("respones error");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //httpClient.close();
        }

        return detailUri;
    }


    /**
     * 根据URI 获取详情
     *
     * @param detailUri 详情地址
     * @return
     */
    public static List<String> getDetai(String detailUri) {
        List<String> data = new ArrayList<String>();

        //创建客户端
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //创建请求Get实例
        HttpGet httpGet = new HttpGet(detailUri);

        //设置头部信息进行模拟登录（添加登录后的Cookie）
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        //httpGet.setHeader("Cookie", ".......");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);

            //得到服务响应状态码
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = closeableHttpResponse.getEntity();
                String html = EntityUtils.toString(entity, "utf-8");
                //System.err.println(html);
                Document document = Jsoup.parse(html);
                Elements elements = document.getElementsByClass("drugDecri");

                for (int i = 0; i < elements.size(); i++) {
                    String str = elements.get(i).text();
                    System.out.println(str);
                    data.add(str);
                }
            } else {
                //如果是其他状态码则做其他处理
                System.err.println("respones error");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //httpClient.close();
        }

        return data;
    }

}
