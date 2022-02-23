package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 文件和文件夹信息工具类
 */
public class MyFileVo {
    //名称
    private String name;
    //状态
    private String status;
    //路径
    private String path;
    //修改日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modTime;
    //创建日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creTime;
    //删除日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    //扩展名
    private String extendName;
    //类型名称
    private String typeName;
    //文件大小
    private Integer size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getExtendName() {
        return extendName;
    }

    public void setExtendName(String extendName) {
        this.extendName = extendName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileVo{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", path='" + path + '\'' +
                ", modTime=" + modTime +
                ", creTime=" + creTime +
                ", delTime=" + delTime +
                ", extendName='" + extendName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", size=" + size +
                '}';
    }
}
