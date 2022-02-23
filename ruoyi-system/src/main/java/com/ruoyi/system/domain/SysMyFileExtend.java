package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class SysMyFileExtend extends BaseEntity {
    private Integer extendId;
    private String extendName;
    private Integer typeId;

    @Override
    public String toString() {
        return "SysFileExtend{" +
                "extendId=" + extendId +
                ", extendName='" + extendName + '\'' +
                ", typeId=" + typeId +
                '}';
    }

    public Integer getExtendId() {
        return extendId;
    }

    public void setExtendId(Integer extendId) {
        this.extendId = extendId;
    }

    public String getExtendName() {
        return extendName;
    }

    public void setExtendName(String extendName) {
        this.extendName = extendName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
