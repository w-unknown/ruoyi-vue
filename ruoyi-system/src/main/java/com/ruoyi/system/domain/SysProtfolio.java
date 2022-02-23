package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

public class SysProtfolio extends BaseEntity {
    private Integer proId;
    private String proName;
    private String proStatus;
    private String proPath;
    private Integer proParentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    private Integer proSize;

    @Override
    public String toString() {
        return "SysProtfolio{" +
                "proId=" + proId +
                ", proName='" + proName + '\'' +
                ", proStatus='" + proStatus + '\'' +
                ", proPath='" + proPath + '\'' +
                ", proParentId=" + proParentId +
                ", modTime=" + modTime +
                ", creTime=" + creTime +
                ", delTime=" + delTime +
                ", proSize=" + proSize +
                '}';
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public String getProPath() {
        return proPath;
    }

    public void setProPath(String proPath) {
        this.proPath = proPath;
    }

    public Integer getProParentId() {
        return proParentId;
    }

    public void setProParentId(Integer proParentId) {
        this.proParentId = proParentId;
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

    public Integer getProSize() {
        return proSize;
    }

    public void setProSize(Integer proSize) {
        this.proSize = proSize;
    }
}
