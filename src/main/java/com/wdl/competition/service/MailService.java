package com.wdl.competition.service;

public interface MailService {

    /*
     * 发送简单文本的邮件
     * @param to
     * @param subject
     * @param content
     * @return
     */
    boolean sendSimpleText(String to, String subject, String content);



    /*(两个附件)
     * 发送带有附件的邮件
     * @param to
     * @param filePaths
     * @return
     */
    boolean sendWithWithEnclosure(String to,String filePathA,String filePathB,String datatype);



    /*
     * 发送带有附件的邮件
     * @param to
     * @param filePaths
     * @return
     */
    boolean sendWithWithEnclosure(String to,String filePath,String datatype);



    /*(多个附件)
     * 发送带有附件的邮件
     * @param to
     * @param filePaths
     * @return
     */
    public boolean ManysendWithWithEnclosure(String to,String filePath ,String folderPath, String datatype);



}
