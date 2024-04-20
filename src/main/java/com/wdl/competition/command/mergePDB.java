package com.wdl.competition.command;

import java.io.IOException;

public class mergePDB {

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

        String scriptPath = "E:/anaconda/envs/pytorch/python.exe E:/FTP/web/data/test.py";

        // 构建命令
        String command =  scriptPath;

        //下面两行代码是最后打包时要写的，如果报错，将下面注释的代码解除注释，并将这两行代码注释掉
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    public void executeLinuxCommands() throws IOException, InterruptedException {
        // 执行 Linux 下的命令
        // 定义要执行的命令
        String command1 = "/data/ycp/web/PDB/hdock /data/ycp/web/PDB/1CGI_r_b.pdb /data/ycp/web/PDB/1CGI_l_b.pdb -out /data/ycp/web/PDB/Hdock.out";
        String command2 = "/data/ycp/web/PDB/createpl /data/ycp/web/PDB/Hdock.out /data/ycp/web/PDB/top1.pdb -nmax 1 -complex -models";

        // 构建命令数组
        String[] commands = {command1, command2};

        // 使用 ProcessBuilder 构建并执行命令
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true); // 将标准输出和错误输出合并
        Process process = pb.start();
        process.waitFor(); // 等待命令执行完成
    }
}
