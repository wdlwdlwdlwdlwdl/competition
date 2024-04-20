package com.wdl.competition.controller;

import com.wdl.competition.model.FileInfo;
import com.wdl.competition.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

@Controller
public class PDBfileController {
        private static String fileUploadRootDir = null;

        //PDB 文件上传的地方
        @Value("${PDBfile.upload.root.dir.windows}")
        String PDBfileUploadRootDirWindows;

        private static Map<String, FileInfo> fileRepository = new HashMap<>();

        // 将上传的文件改名为
        @Value("${PDBfileName1}")
        String filename1;

        @Value("${PDBfileName2}")
        String filename2;

        @PostMapping(value = "/uploadPDB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @ResponseBody
        public String fileUploadPDB(@RequestParam("file1") MultipartFile file1,
                                    @RequestParam("file2") MultipartFile file2) throws IOException {

            // 根据操作系统设置文件上传根目录
            String osName = System.getProperty("os.name");
            if (osName.startsWith("Windows")) {
                fileUploadRootDir = PDBfileUploadRootDirWindows;
            }
            else {
                fileUploadRootDir = PDBfileUploadRootDirWindows;
            }

            FileUtil.createDirectories(fileUploadRootDir);

            // 获取文件上传目录
            String uploadDir = fileUploadRootDir;
            if (!uploadDir.endsWith(File.separator)) {
                uploadDir += File.separator;
            }

            // 保存第一个文件
            File convertFile1 = new File(uploadDir + filename1);
            FileOutputStream fileOutputStream1 = new FileOutputStream(convertFile1);
            fileOutputStream1.write(file1.getBytes());
            fileOutputStream1.close();

            // 保存第二个文件
            File convertFile2 = new File(uploadDir + filename2);
            FileOutputStream fileOutputStream2 = new FileOutputStream(convertFile2);
            fileOutputStream2.write(file2.getBytes());
            fileOutputStream2.close();

            // 将文件信息保存到文件仓库中
            FileInfo fileInfo1 = new FileInfo();
            fileInfo1.setFileName(file1.getOriginalFilename());
            fileRepository.put(fileInfo1.getName(), fileInfo1);

            FileInfo fileInfo2 = new FileInfo();
            fileInfo2.setFileName(file2.getOriginalFilename());
            fileRepository.put(fileInfo2.getName(), fileInfo2);

            return "Files are uploaded successfully";
        }



}
