package com.ruoyi.system.domain.vo;

/**
 * @author LanZhiWei
 * @date 2022/02/15 11:30
 *
 * Demo信息类
 **/
public class DemoVo {
    private String fieldValue;
    private Integer sum;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
