package com.wdl.competition.controller.TCRcontroller;

import com.wdl.competition.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TCRfile {
    private static String fileUploadRootDir = null;

    @Autowired
    private MailService mailService;

    // 通过@Value注解从properties文件中读取文件路径(运行结果)
    @Value("${TCRFilePath}")
    String TCRfilePath;

    @Value("${HotPathTCR}")
    String TCRhotPath;


    @ResponseBody
    @PostMapping("/submitFormTCRfile")
    public String HLAfile(@RequestParam(value = "checkbox", required = false) boolean checkbox,
                          @RequestParam("Email_address") String Email_address){

        boolean result=false;
        if (checkbox) {
            result = mailService.ManysendWithWithEnclosure(Email_address, TCRfilePath,TCRhotPath,"TCR");
        } else {
            result = mailService.sendWithWithEnclosure(Email_address, TCRfilePath,"TCR");
        }

        //boolean result = mailService.sendWithWithEnclosure(Email_address, filePath);
        if (result) {
            return "邮件发送成功！";
        } else {
            return "带附件的邮件发送失败，请检查日志获取详细信息。";
        }

    }
}