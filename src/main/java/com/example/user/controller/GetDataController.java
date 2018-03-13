package com.example.user.controller;

import com.example.user.entity.KmData;
import com.example.user.service.KmDataService;
import com.example.user.util.MainBen2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

    @GetMapping("/go")
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

                    Thread.sleep(2000);

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}