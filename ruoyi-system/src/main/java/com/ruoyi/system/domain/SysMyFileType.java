package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class SysMyFileType extends BaseEntity {
    private Integer typeId;
    private String typeName;

    @Override
    public String toString() {
        return "SysFileType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
