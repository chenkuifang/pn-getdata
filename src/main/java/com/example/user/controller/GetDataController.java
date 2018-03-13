package com.example.user.controller;

import com.example.user.entity.KmData;
import com.example.user.service.KmDataService;
import com.example.user.util.MainBen2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author QuiFar
 * @version V1.0
 **/
@RestController
public class GetDataController {

    @Autowired
    private KmDataService kmDataService;

    @GetMapping("/index1")
    public String index1() {
        return "hello world";
    }


    /**
     * 清洗数据
     *
     * @return
     */
    @GetMapping("/clear")
    public String clearData() {
        List<KmData> list = kmDataService.list(null);

        System.err.println(list.size());

        String clearResult = "null";
        for (int i = 0, len = list.size(); i < len; i++) {
            int id = list.get(i).getId();
            String exp = list.get(i).getExplain1();
            int index = exp.lastIndexOf("分享到");
            if (index > 0) {
                clearResult = exp.substring(0, index);

                int index2 = clearResult.lastIndexOf("分享到");

                if (index2 > 0) {
                    clearResult = clearResult.substring(0, index2);
                }

            }


            //System.err.println(clearResult);

            kmDataService.updateExplains(id + "", clearResult);

        }

        return "clear....";
    }


    @GetMapping("/go1")
    public String getexplanData() {

        // 说明书
        List<String> explains = null;

        try {

            List<KmData> list = kmDataService.list(null);

            String documentNumber;
            Integer id;
            for (KmData kmData : list) {
                // 非空
                if (StringUtils.isNotBlank(kmData.getDocumentNumber())) {
                    String url = "http://www.yaopinnet.com/tools/wenhao.asp?keyword=";
                    documentNumber = kmData.getDocumentNumber();
                    id = kmData.getId();
                    url += documentNumber;

                    System.err.println("uri:" + url);

                    //Thread.sleep(1000);

                    // 爬数据
                    explains = MainBen2.getDetailUrl(url);

                    if (!explains.isEmpty()) {
                        StringBuilder str = new StringBuilder();
                        for (String obj : explains) {
                            str.append(obj);
                        }

                        // 插入数据库
                        if (id != null && str.toString() != "") {
                            kmDataService.updateExplains(id + "", str.toString());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
