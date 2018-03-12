package com.example.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.entity.KmData;
import com.example.user.util.ReadExcel;
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

    @GetMapping("/index")
    public String index() {
        return "hello world";
    }

    @GetMapping("/get-data")
    public String getData() {
        String fileName = "E:/km-data.xlsx";
        try {
            List<KmData> data = ReadExcel.readXls(fileName);

            System.err.println(data.size());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "hello world";
    }
}
