package com.wdl.competition.controller;


import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class DockController {

    @PostMapping("/submitDockRequest")
    @ResponseBody
    public ResponseEntity<String> submitDockRequest(@RequestBody DockRequest dockRequest) {
        // 目标网站 URL
        String targetUrl = "http://hdock.phys.hust.edu.cn/";

        // 构建需要提交的表单数据
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("fastaseq1", dockRequest.getTcrSequence());
        formData.add("fastaseq2", dockRequest.getAntigenSequence());

        // 创建一个 HTTP 请求实体
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        // 使用 RestTemplate 发送 POST 请求到目标网站，并设置跟随重定向
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(targetUrl, HttpMethod.POST, requestEntity, String.class);

        // 返回目标网站的响应
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}


