package com.example.user.controller;

import com.example.user.entity.KmData;
import com.example.user.service.KmDataService;
import com.example.user.util.HttpClientUtil;
import com.example.user.util.ReadExcel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 控制器
 *
 * @author QuiFar
 */
@RestController
public class IndexController {

    private final String fileName = "E:/km-data.xlsx";

    @Autowired
    private KmDataService kmDataService;

    @GetMapping("/index")
    public String index() {
        return "hello world";
    }

    @GetMapping("/get-data")
    public String getData() {

        int size = 0;
        try {
            List<KmData> data = ReadExcel.readXls(fileName);
            size = data.size();
            System.err.println(data.size());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "total :" + size;
    }


    @GetMapping("/add-batch")
    public String addBatch() {

        int result = 0;
        try {
            List<KmData> data = ReadExcel.readXls(fileName);

            //result = kmDataService.addBatch(data);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return result > 0 ? "ok" : "fail";
    }


    /**
     * 获取第一个节点网址
     *
     * @return
     */
    @GetMapping("/get")
    public String get() {
        try {
            //return getFirstSpan("国药准字Z35020005");\
            return getSecondeSpan("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取第一跨度链接
     *
     * @return
     */
    private String getFirstSpan(String paramsValue) throws Exception {
        String result = HttpClientUtil.doGet("http://www.yaopinnet.com/tools/wenhao.asp", "keyword", paramsValue);
        Document doc = Jsoup.parse(result);
        Element element = doc.body();
        Element e = element.getElementById("search_list_table");
        String html = e.html();

        //第二个A标签的位置
        int startLastIndex = html.lastIndexOf("<a");
        int endLastIndex = html.lastIndexOf("</a>");

        String aRestult = html.substring(startLastIndex, endLastIndex);

        // 截取 a 标签的 href 属性值
        int startIndex = aRestult.indexOf("href=");
        int endIndex = aRestult.indexOf(".htm");

        String href = aRestult.substring(startIndex + 6, endIndex);
        String completeUri = "http://www.yaopinnet.com".concat(href).concat(".htm");
        //System.err.println(completeUri);
        return completeUri;
    }


    /**
     * 获取第二跨度链接
     *
     * @return
     */
    private String getSecondeSpan(String uri) throws Exception {
        //String result = HttpClientUtil.doGet(uri);

        String result = HttpClientUtil.doGet("http://www.yaopinnet.com/z05/Z35020005.htm");
        Document doc = Jsoup.parse(result);
        Element element = doc.body();

        Elements e = element.getElementsByClass("w_content");
        //e.a
        System.err.println(e.html());

        return "";
    }


}
