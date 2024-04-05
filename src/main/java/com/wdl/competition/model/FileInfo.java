package com.wdl.competition.model;

import lombok.Data;

import java.util.Date;

@Data
public class FileInfo {

    private String name;
    private Date uploadTime = new Date();

    public FileInfo setFileName(String name) {
        this.setName(name);
        return this;
    }
}