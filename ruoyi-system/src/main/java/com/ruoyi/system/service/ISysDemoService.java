package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysDemo;
import com.ruoyi.system.domain.vo.DemoVo;

import java.util.List;

public interface ISysDemoService {
    /**
     * 获取Demo列表
     * @param sysDemo
     * @return
     */
    public List<SysDemo> getSysDemoList(SysDemo sysDemo);

    /**
     * 根据字段注释获取sys_demo表的字段名称
     * @param comment
     * @return
     */
    public List<DemoVo> getSysDemoFields(String comment);

    /**
     * 根据条件查询图表数据
     * @param sysDemo
     * @return
     */
    public List<DemoVo> groupByField(SysDemo sysDemo);
}
