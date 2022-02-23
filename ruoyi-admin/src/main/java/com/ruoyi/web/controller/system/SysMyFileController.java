package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.vo.MyFileVo;
import com.ruoyi.system.service.ISysMyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/file")
public class SysMyFileController extends BaseController {

    @Autowired
    private ISysMyFileService sysFileService;

    @GetMapping("/list")
    public TableDataInfo getFileList(String name,String path){
        List<MyFileVo> fileList = sysFileService.getFileList(name, path);
        System.out.println(fileList);
        return getDataTable(fileList);
    }
}
