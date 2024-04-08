package com.wdl.competition.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.BufferedReader;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.FileReader;






@Controller
public class table {

    @Value("${TCRFilePath}")
    private String filepath;

    @GetMapping("/readCsv")
    @ResponseBody
    public String readCsv() {
        StringBuilder csvContent = new StringBuilder();
//        FileReader 用于创建一个读取文件的字符输入流
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                csvContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvContent.toString();
    }
}





