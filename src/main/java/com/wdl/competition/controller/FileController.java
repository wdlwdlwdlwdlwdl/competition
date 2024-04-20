package com.wdl.competition.controller;

import com.wdl.competition.model.FileInfo;
import com.wdl.competition.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {
    private static String fileUploadRootDir = null;

    //    TCR上传文件的地址
    @Value("${TCRfile.upload.root.dir.windows}")
    String TCRfileUploadRootDirWindows;



    private static Map<String, FileInfo> fileRepository = new HashMap<>();


//    @RequestMapping("/")
//    public RedirectView redirectToIndex() {
//        return new RedirectView("/index.html");
//    }




    // 将上传的文件改名为 independent.csv
    @Value("${fileName}")
    String filename;


    @PostMapping(value = "/uploadTCR", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String fileUploadTCR(@RequestParam("file") MultipartFile file) throws IOException {


        String osName = System.getProperty("os.name");
//        if (osName.startsWith("Windows")) {
//            fileUploadRootDir = TCRfileUploadRootDirWindows;
//        }
        fileUploadRootDir = TCRfileUploadRootDirWindows;
        FileUtil.createDirectories(fileUploadRootDir);


//        String uploadDir = getUploadDir();
        String uploadDir = fileUploadRootDir;

        if (!uploadDir.endsWith(File.separator)) {
            uploadDir += File.separator;
        }
        File convertFile = new File(uploadDir + filename);
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());

        fileRepository.put(fileInfo.getName(), fileInfo);

        return "File is uploaded successfully";
    }

}