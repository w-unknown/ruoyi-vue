package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.MyFileVo;

import java.util.List;

public interface ISysMyFileService {
    public List<MyFileVo> getFileList(String name, String path);
}
