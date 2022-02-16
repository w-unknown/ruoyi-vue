package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysDemo;
import com.ruoyi.system.domain.vo.DemoVo;
import com.ruoyi.system.service.ISysDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LanZhiWei
 * @date 2022/02/14 13:06
 **/

@RestController
@RequestMapping("/system/demo")
public class SysDemoController extends BaseController {

    private final static String COMMENT="维度";
    @Autowired
    private ISysDemoService ISysDemoService;

    @GetMapping("/list")
    public TableDataInfo getSysDemoList(SysDemo sysDemo){
        startPage();
        List<SysDemo> sysDemoList = ISysDemoService.getSysDemoList(sysDemo);
        return getDataTable(sysDemoList);
    }

    @GetMapping("/chart")
    public AjaxResult groupByField(SysDemo sysDemo){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("chart",ISysDemoService.groupByField(sysDemo));
        return ajax;
    }

    @GetMapping("/fields")
    public AjaxResult getFields(){
        AjaxResult ajax = AjaxResult.success();
        List<DemoVo> sysDemoFields = ISysDemoService.getSysDemoFields(null);
        List<DemoVo>  sysDemoFields2= ISysDemoService.getSysDemoFields(COMMENT);
        subString(sysDemoFields);
        subString(sysDemoFields2);
        ajax.put(AjaxResult.DATA_TAG, sysDemoFields);
        ajax.put("fields1",sysDemoFields);
        ajax.put("fields2",sysDemoFields2);
        return ajax;
    }

    //截取注释作为下拉框的label
    public static void subString(List<DemoVo> demoVos){
        for (DemoVo demoVo : demoVos) {
            String comment = demoVo.getComment();
            int indexOf = comment.indexOf('-');
            if(indexOf>0){
                String substring = comment.substring(0, indexOf);
                demoVo.setComment(substring);
            }
        }
    }
}
