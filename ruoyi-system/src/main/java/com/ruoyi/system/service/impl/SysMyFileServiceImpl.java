package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysMyFile;
import com.ruoyi.system.domain.SysMyFileExtend;
import com.ruoyi.system.domain.SysProtfolio;
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
    public List<MyFileVo> getFileVoList(MyFileVo myFileVo) {
        return sysFileMapper.getFileVoList(myFileVo);
    }

    @Override
    public List<SysMyFile> getFileList(SysMyFile sysMyFile) {
        return sysFileMapper.getFileList(sysMyFile);
    }

    @Override
    public Integer getDownFileOrProSumSize(MyFileVo myFileVo) {
        return sysFileMapper.getDownFileOrProSumSize(myFileVo);
    }

    @Override
    public List<SysMyFileExtend> getExtendList(SysMyFileExtend sysMyFileExtend) {
        return null;
    }

    @Override
    public int addFile(SysMyFile sysMyFile) {
        return 0;
    }

    @Override
    public int addProtfolio(SysProtfolio sysProtfolio) {
        return 0;
    }

    @Override
    public int editFile(SysMyFile sysMyFile) {
        return 0;
    }

    @Override
    public int editProtfolio(SysProtfolio protfolio) {
        return 0;
    }

    @Override
    public int delFile(List<MyFileVo> list) {
        return 0;
    }

    @Override
    public int delProtfolio(List<MyFileVo> list) {
        return 0;
    }
}
