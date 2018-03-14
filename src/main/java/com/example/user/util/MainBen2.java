package com.example.user.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/***
 * 药源网 搜索
 */
public class MainBen2 {

    public static void main(String[] args) throws IOException {

        String audit_number = "h22026232";
        String url = "http://www.yaopinnet.com/tools/wenhao.asp?keyword=" + audit_number;
        getDetailUrl(url);

    }

    public static List<String> getDetailUrl(String url) throws IOException {

        // 创建请求
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        // 默认就是Get，可以采用post，大小写都行，因为源码里都toUpperCase了。
        connection.setRequestMethod("GET");
        // 是否允许缓存，默认true。
        connection.setUseCaches(Boolean.FALSE);
        // 是否开启输出输入，如果是post使用true。默认是false
        // connection.setDoOutput(Boolean.TRUE);
        // connection.setDoInput(Boolean.TRUE);
        // 设置请求头信息
        connection.addRequestProperty("Connection", "close");
        // 设置连接主机超时（单位：毫秒）
        connection.setConnectTimeout(8000);
        // 设置从主机读取数据超时（单位：毫秒）
        connection.setReadTimeout(8000);

        // 解决乱码问题
        // 开始请求

        Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", url);


        Elements elements = doc.getElementsByAttributeValueStarting("id", "search_list_table");

        if (elements.isEmpty()) {
            return new ArrayList<String>();
        }

        Elements es = doc.getElementsByAttributeValueStarting("id", "search_list_table").get(0).getElementsByTag("a");
        String detailUrl = es.get(1).absUrl("href");
        System.out.println(detailUrl);
        return getSpecificationUrl(detailUrl);
    }

    public static List<String> getSpecificationUrl(String url) throws IOException {

        // 创建请求
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        // 默认就是Get，可以采用post，大小写都行，因为源码里都toUpperCase了。
        connection.setRequestMethod("GET");
        // 是否允许缓存，默认true。
        connection.setUseCaches(Boolean.FALSE);
        // 是否开启输出输入，如果是post使用true。默认是false
        // connection.setDoOutput(Boolean.TRUE);
        // connection.setDoInput(Boolean.TRUE);
        // 设置请求头信息
        connection.addRequestProperty("Connection", "close");
        // 设置连接主机超时（单位：毫秒）
        connection.setConnectTimeout(8000);
        // 设置从主机读取数据超时（单位：毫秒）
        connection.setReadTimeout(8000);

        Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
        Elements elements = doc.getElementsByClass("w_content1");// 得到药品说明书的DIV
        Elements aList = elements.get(0).getElementsByTag("a");
        String specificationUrl = "";
        for (int i = 0; i < aList.size(); i++) {
            Element a = aList.get(i);
            String acontent = a.html();
            if (StringUtils.isNotBlank(acontent) && acontent.contains("说明书")) {

                specificationUrl = a.absUrl("href");
            }
        }
        System.out.println(specificationUrl);
        return getInstructionsByUrl(specificationUrl);

    }

    public static List<String> getInstructionsByUrl(String url) throws IOException {

        List<String> data = new ArrayList<String>();

        // 创建请求
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        // 默认就是Get，可以采用post，大小写都行，因为源码里都toUpperCase了。
        connection.setRequestMethod("GET");
        // 是否允许缓存，默认true。
        connection.setUseCaches(Boolean.FALSE);
        // 是否开启输出输入，如果是post使用true。默认是false
        // connection.setDoOutput(Boolean.TRUE);
        // connection.setDoInput(Boolean.TRUE);
        // 设置请求头信息
        connection.addRequestProperty("Connection", "close");
        // 设置连接主机超时（单位：毫秒）
        connection.setConnectTimeout(8000);
        // 设置从主机读取数据超时（单位：毫秒）
        connection.setReadTimeout(8000);

        Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", url);

        Elements e = doc.getElementsByClass("sms_content");
        Elements elements;

        if (e.isEmpty()) {
            Element element = doc.getElementById("sms_content");
            elements = element.select("div");

            //elements = doc.getElementById("sms_content").select("li");
        } else {
            elements = doc.getElementsByClass("sms_content").select("li");// 得到药品说明书的DIV
        }


        for (int i = 0; i < elements.size(); i++) {

            String str = elements.get(i).text();

//            if (str.indexOf("如有问题") > 0) {
//                continue;
//            } else if (str.indexOf("分享到") > 0) {
//                continue;
//            } else if (str.indexOf("微信扫一扫") > 0) {
//                continue;
//            }

            System.out.println(elements.get(i).text());


            data.add(str);
        }

        return data;

    }

}
