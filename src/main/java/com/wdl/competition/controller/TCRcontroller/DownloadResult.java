package com.wdl.competition.controller.TCRcontroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class DownloadResult {

    //  运行结果下载处理逻辑
    @Value("${TCRFilePath}")
    private String FilePath;

    @GetMapping("/DownloadResult")
    @ResponseBody
    public ResponseEntity<Object> downloadFile2() {

        String filePath = FilePath;
        File file = new File(filePath);

        if (file.exists()) {
            try {
                String actualFileName = file.getName();

                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment;filename=" + encodeFileNameToUTF8(actualFileName));
                headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");



                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (FileNotFoundException e) {
                return ResponseEntity.status(500).body("Internal Server Error");
            }
        } else {
            return ResponseEntity.status(404).body("File not found");
        }
    }


    private String encodeFileNameToUTF8(String fileName) {
        try {
            return URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return fileName;
        }
    }

}
