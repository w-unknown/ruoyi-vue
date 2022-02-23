package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.vo.MyFileVo;
import com.ruoyi.system.mapper.SysMyFileMapper;
import com.ruoyi.system.service.ISysMyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMyFileServiceImpl implements ISysMyFileService {

    @Autowired
    private SysMyFileMapper sysFileMapper;

    @Override
    public List<MyFileVo> getFileList(String name, String path) {
        return sysFileMapper.getFileList(name,path);
    }
}
