package com.wdl.competition.command;

import java.io.IOException;

public class TCRhotCommand {
    public void executeShellCommands() {
        String osName = System.getProperty("os.name");

        try {
            if (osName.startsWith("Windows")) {
                executeWindowsCommands();
            } else if (osName.startsWith("Linux")) {
                executeLinuxCommands();
            } else {
                System.err.println("Unsupported operating system: " + osName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeWindowsCommands() throws IOException, InterruptedException {
        // 完整的 Python 解释器路径
//        String pythonPath = "E:/anaconda/envs/pytorch/python.exe";

        // 第一个脚本路径
        //本地测试
        String scriptPath1 = "E:/anaconda/envs/pytorch/python.exe E:/FTP/web/data/性能测试_tcr.py";

        //服务器
//        String scriptPath1 = "python E:/FTP/web/data/性能测试_tcr.py";

        String command1 =  scriptPath1;





        // 第二个脚本路径
        //本地测试
        String scriptPath2 = "E:/anaconda/envs/pytorch/python.exe E:/FTP/web/data/热图_tcr.py";

        //服务器
//        String scriptPath2 = "python E:/FTP/web/data/热图_tcr.py";

        String command2 =  scriptPath2;






        // 执行第一个命令
        Process process1 = Runtime.getRuntime().exec(command1);
        process1.waitFor();

        // 执行第二个命令
        Process process2 = Runtime.getRuntime().exec(command2);
        process2.waitFor();

    }


    public void executeLinuxCommands() throws IOException, InterruptedException {
        // 执行 Linux 下的命令
        // 例如：source activate ycp
        Process process1 = Runtime.getRuntime().exec("/data/ycp/web/data/热图_tcr.py");
        process1.waitFor();
    }
}
