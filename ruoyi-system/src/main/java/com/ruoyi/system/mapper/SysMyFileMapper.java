package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysMyFile;
import com.ruoyi.system.domain.SysMyFileExtend;
import com.ruoyi.system.domain.SysProtfolio;
import com.ruoyi.system.domain.vo.MyFileVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMyFileMapper {
    //查询文件信息用于显示
    public List<MyFileVo> getFileVoList(MyFileVo myFileVo);

    //查询文件信息用户判断
    public List<SysMyFile> getFileList(SysMyFile sysMyFile);

    /**
     * 获取文件夹下所有未删除的文件的大小
     */
    public Integer getDownFileOrProSumSize(MyFileVo myFileVo);

    /**
     * 获取文件后缀名信息
     * @return
     */
    public List<SysMyFileExtend> getExtendList(SysMyFileExtend sysMyFileExtend);

    /**
     * 新增文件
     * @return
     */
    public int addFile(SysMyFile sysMyFile);

    /**
     * 新增文件夹
     * @return
     */
    public int addProtfolio(SysProtfolio sysProtfolio);

    /**
     * 修改文件
     * @return
     */
    public int editFile(SysMyFile sysMyFile);
    /**
     * 修改文件夹
     * @return
     */
    public int editProtfolio(SysProtfolio protfolio);

    /**
     * 根据文件名和路径删除文件
     * @return
     */
    public int delFile(@Param("list") List<MyFileVo> list);

    /**
     * 根据文件夹名和路径删除文件
     * @return
     */
    public int delProtfolio(@Param("list") List<MyFileVo> list);


}
