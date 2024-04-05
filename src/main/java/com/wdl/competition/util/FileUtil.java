package com.wdl.competition.util;

import java.io.File;

public class FileUtil {
    public static void createDirectories(String pathname) {
        File directories = new File(pathname);
        if (directories.exists()) {
            System.out.println("File upload root directory already exists");
        } else {
//            尝试创建多级目录。使用 mkdirs() 方法，该方法会创建该目录及其所有不存在的父目录。如果创建成功，则输出成功消息。
            if (directories.mkdirs()) {
                System.out.println("Created multiple directories successfully");
            } else {
                System.out.println("Failed to create multiple directories");
            }
        }
    }
}
