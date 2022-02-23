package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.vo.MyFileVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMyFileMapper {
    public List<MyFileVo> getFileList(@Param("name") String name,@Param("path") String path);
}
