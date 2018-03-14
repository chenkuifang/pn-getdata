package com.example.user.controller;

import com.example.user.entity.KmData;
import com.example.user.service.KmDataService;
import com.example.user.util.MainBen1;
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


    @GetMapping("/update-data")
    public String updateData() {

        //获得表1 说明非空的数据列表
        List<KmData> list = kmDataService.list(null);
        System.err.println("需要修改的数量：" + list.size());
        for (KmData kmData : list) {
            String id = kmData.getId() + "";
            String exp = kmData.getExplain1();

            kmDataService.updateExplains(id, exp);

        }

        return "OK";
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

    @GetMapping("/get-document")
    public String getDocumentNumber() {
        List<KmData> list = kmDataService.list(null);

        if (!list.isEmpty()) {
            for (KmData kmData : list) {
                String documentNumber = kmData.getDocumentNumber();
                int id = kmData.getId();
                if (documentNumber.length() > 9) {
                    int index = documentNumber.indexOf("（原");
                    if (index > 0) {
                        documentNumber = documentNumber.substring(0, index);
                        System.err.println(documentNumber);
                    }

                    kmDataService.updateDocument(id + "", documentNumber);
                    //System.err.println(documentNumber);
                }

                //System.err.println(documentNumber);

            }
        }

        return "get document number";
    }

    @GetMapping("/go1")
    public String getexplanData() {

        // 说明书
        List<String> explains = null;

        List<KmData> list = kmDataService.list(null);

        String documentNumber;
        Integer id;
        for (KmData kmData : list) {
            // 非空
            if (StringUtils.isNotBlank(kmData.getDocumentNumber())) {
                String url = "http://yp.120ask.com/search?kw=";
                documentNumber = kmData.getDocumentNumber();
                id = kmData.getId();

                url += documentNumber.trim();

                System.err.println("uri:" + url);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 爬数据
                String detailUri = MainBen1.getDetailUri(url);

                if (StringUtils.isNotBlank(detailUri)) {
                    detailUri = "http:" + detailUri;
                    explains = MainBen1.getDetai(detailUri);
                } else {
                    continue;
                }

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

        return "";
    }
}
