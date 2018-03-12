package com.example.user.controller;

import com.example.user.entity.KmData;
import com.example.user.service.KmDataService;
import com.example.user.util.ReadExcel;
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

    @Autowired
    private KmDataService kmDataService;

    @GetMapping("/index")
    public String index() {
        return "hello world";
    }

    @GetMapping("/get-data")
    public String getData() {
        String fileName = "D:/km-data.xlsx";
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
        String fileName = "D:/km-data.xlsx";
        int result = 0;
        try {
            List<KmData> data = ReadExcel.readXls(fileName);

            result = kmDataService.addBatch(data);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return result > 0 ? "ok" : "fail";
    }

}
