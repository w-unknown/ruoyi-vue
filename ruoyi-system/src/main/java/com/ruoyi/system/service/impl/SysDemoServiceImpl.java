package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysDemo;
import com.ruoyi.system.domain.vo.DemoVo;
import com.ruoyi.system.mapper.SysDemoMapper;
import com.ruoyi.system.service.ISysDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LanZhiWei
 * @date 2022/02/14 14:23
 **/
@Service
public class SysDemoServiceImpl implements ISysDemoService {

    @Autowired
    private SysDemoMapper sysDemoMapper;

    @Override
    public List<SysDemo> getSysDemoList(SysDemo sysDemo) {
        return sysDemoMapper.getSysDemoList(sysDemo);
    }

    @Override
    public List<DemoVo> getSysDemoFields(String comment) {
        return sysDemoMapper.getSysDemoFields(comment);
    }

    @Override
    public List<DemoVo> groupByField(SysDemo sysDemo) {
        return sysDemoMapper.groupByField(sysDemo);
    }
}
