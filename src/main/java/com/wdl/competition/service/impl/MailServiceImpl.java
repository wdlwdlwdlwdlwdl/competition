package com.wdl.competition.service.impl;

import com.wdl.competition.command.PDBCommand;
import com.wdl.competition.command.TCRCommand;
import com.wdl.competition.command.TCRhotCommand;
import com.wdl.competition.command.mergePDB;
import com.wdl.competition.service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailServiceImpl implements MailService {

    private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendSimpleText(String to, String subject, String content) {
        logger.info("## Ready to send mail ...");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 邮件发送来源
        simpleMailMessage.setFrom(mailProperties.getUsername());
        // 邮件发送目标
        simpleMailMessage.setTo(to);
        // 设置标题
        simpleMailMessage.setSubject(subject);
        // 设置内容
        simpleMailMessage.setText(content);

        try {
            // 发送
            javaMailSender.send(simpleMailMessage);
            logger.info("## Send the mail success ...");
        } catch (Exception e) {
            logger.error("Send mail error: ", e);
            return false;
        }

        return true;
    }




    //预测结果
    @Override
    public boolean sendWithWithEnclosure(String to, String filePath,String datatype) {

        if(datatype.equals("TCR")){
            TCRCommand commandExecutor=new TCRCommand();
            commandExecutor.executeShellCommands();
        }

//        if(datatype.equals("PDB")){
//            mergePDB commandPDB=new mergePDB();
//            commandPDB.executeShellCommands();
//
//            PDBCommand commandExecutor=new PDBCommand();
//            commandExecutor.executeShellCommands();
//        }

        logger.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("标题：运行结果");
            mimeMessageHelper.setText("运行结果");

            FileSystemResource file = new FileSystemResource(filePath);
            //String attachementFileName = "附件_" + file.getFilename();
            String attachementFileName = file.getFilename();
            mimeMessageHelper.addAttachment(attachementFileName, file);

            javaMailSender.send(mimeMessage);
            logger.info("## Send the mail with enclosure success ...");

        } catch (Exception e) {
            logger.error("Send html mail error: ", e);
            return false;
        }
        return true;
    }



    //此函数只能发送两个附件（已舍弃）
    @Override
    public boolean sendWithWithEnclosure(String to, String filePathA, String filePathB,String datatype) {



        if(datatype.equals("TCR")){
            TCRCommand commandExecutor=new TCRCommand();
            commandExecutor.executeShellCommands();
        }



        logger.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("标题：运行结果");
            mimeMessageHelper.setText("运行结果");

            // 添加第一个附件
            FileSystemResource fileA = new FileSystemResource(filePathA);
            String attachmentFileNameA = fileA.getFilename();
            mimeMessageHelper.addAttachment(attachmentFileNameA, fileA);

            // 添加第二个附件
            FileSystemResource fileB = new FileSystemResource(filePathB);
            String attachmentFileNameB = fileB.getFilename();
            mimeMessageHelper.addAttachment(attachmentFileNameB, fileB);

            javaMailSender.send(mimeMessage);
            logger.info("## Send the mail with enclosure success ...");


        } catch (Exception e) {
            logger.error("Send html mail error: ", e);
            return false;
        }
        return true;
    }




    //此函数能发送多个附件（filepath 路径下的附件， folderPath 文件夹下的所有附件）
    @Override
    public boolean ManysendWithWithEnclosure(String to, String filepath, String folderPath, String datatype) {

        // 清空文件夹内的附件
        clearFolderAttachments(folderPath);

        if(datatype.equals("TCR")){
            TCRhotCommand commandExecutor=new TCRhotCommand();
            commandExecutor.executeShellCommands();
        }


        logger.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("标题：运行结果");
            mimeMessageHelper.setText("运行结果");

            // 添加指定路径下的文件作为附件
            if (filepath != null && !filepath.isEmpty()) {
                FileSystemResource file = new FileSystemResource(filepath);
                mimeMessageHelper.addAttachment(file.getFilename(), file);
            }

            // 添加文件夹下的所有文件作为附件
            if (folderPath != null && !folderPath.isEmpty()) {
                File folder = new File(folderPath);
                if (folder.exists() && folder.isDirectory()) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.isFile()) {
                            FileSystemResource resource = new FileSystemResource(file);
                            mimeMessageHelper.addAttachment(file.getName(), resource);
                        }
                    }
                }
            }

            javaMailSender.send(mimeMessage);
            logger.info("## Send the mail with enclosure success ...");

        } catch (Exception e) {
            logger.error("Send html mail error: ", e);
            return false;
        }
        return true;
    }

    //清空 folderPath 路径下所有的附件
    private void clearFolderAttachments(String folderPath) {
        if (folderPath != null && !folderPath.isEmpty()) {
            File folder = new File(folderPath);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        // 删除文件
                        file.delete();
                    }
                }
            }
        }
    }

}
