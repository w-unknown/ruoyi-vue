package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

public class SysMyFile extends BaseEntity {
    private Integer fileId;
    private String fileName;
    private String fileStatus;
    private String filePath;
    private Integer ProId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    private Integer extendId;
    private Integer fileSize;

    @Override
    public String toString() {
        return "SysFile{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", fileStatus='" + fileStatus + '\'' +
                ", filePath='" + filePath + '\'' +
                ", ProId=" + ProId +
                ", modTime=" + modTime +
                ", creTime=" + creTime +
                ", delTime=" + delTime +
                ", extendId=" + extendId +
                ", fileSize=" + fileSize +
                '}';
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getProId() {
        return ProId;
    }

    public void setProId(Integer proId) {
        ProId = proId;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public Integer getExtendId() {
        return extendId;
    }

    public void setExtendId(Integer extendId) {
        this.extendId = extendId;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }
}
