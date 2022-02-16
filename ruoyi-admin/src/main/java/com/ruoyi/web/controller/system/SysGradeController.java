package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysGrade;
import com.ruoyi.system.service.ISysGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author LanZhiWei
 * @date 2022/02/07 17:42
 **/
@RestController
@RequestMapping("/system/grade")
public class SysGradeController extends BaseController {

    @Autowired
    private ISysGradeService sysGradeService;

    //获取学生成绩列表
    @PreAuthorize("@ss.hasPermi('system:grade:list')")
    @GetMapping("/list")
    public TableDataInfo getSysGradeList(SysGrade sysGrade) {
        startPage();
        List<SysGrade> sysGradeList = sysGradeService.getSysGradeList(sysGrade);
        if (sysGradeList.size() > 0 && sysGradeList != null) {
            getSysGradeAvg(sysGradeList);
        }
//        sysGradeList.add(sysGradeService.getSysGradeSum());
        return getDataTable(sysGradeList);
    }

    //求每个学生的平均数
    private static void getSysGradeAvg(List<SysGrade> sysGrades) {
        for (SysGrade sysGrade : sysGrades) {
            double chinese = Double.parseDouble(sysGrade.getChinese());
            double english = Double.parseDouble(sysGrade.getEnglish());
            double mathematics = Double.parseDouble(sysGrade.getMathematics());
            double history = Double.parseDouble(sysGrade.getHistory());
            double biology = Double.parseDouble(sysGrade.getBiology());
            double chemistry = Double.parseDouble(sysGrade.getChemistry());
            double physics = Double.parseDouble(sysGrade.getPhysics());
            double geography = Double.parseDouble(sysGrade.getGeography());
            double politics = Double.parseDouble(sysGrade.getPolitics());
            double avg = (chinese + english + mathematics + history + biology + chemistry + physics + geography + politics) / 9;
            //数字格式化，保留两位小数
            String format = new DecimalFormat("#.00").format(avg);
            sysGrade.setAvg(format);
        }
    }

    //根据学号查询学生详细信息
    @PreAuthorize("@ss.hasPermi('system:grade:id')")
    @GetMapping("/sysGrade/{id}")
    public AjaxResult getSysGradeById(@PathVariable("id") Integer id){
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(id))
        {
            SysGrade sysGradeById = sysGradeService.getSysGradeById(id);
            ajax.put(AjaxResult.DATA_TAG,sysGradeById);
        }
        return ajax;
    }

    //添加学生成绩信息
    @PreAuthorize("@ss.hasPermi('system:grade:add')")
    @PostMapping("/add")
    public AjaxResult insertSysGrade(@Validated @RequestBody SysGrade sysGrade) {
        int i = sysGradeService.insertSysGrade(sysGrade);
        return toAjax(i);
    }

    //修改学生成绩
    @PreAuthorize("@ss.hasPermi('system:grade:edit')")
    @PutMapping("/edit")
    public AjaxResult updateSysGrade(@Validated @RequestBody SysGrade sysGrade) {
        int i = sysGradeService.updateSysGrade(sysGrade);
        return toAjax(i);
    }

    //删除学生成绩信息
    @PreAuthorize("@ss.hasPermi('system:grade:ids')")
    @DeleteMapping("/delete/{ids}")
    public AjaxResult deleteSysGrade(@PathVariable("ids") Integer[] ids) {
        int i = sysGradeService.deleteSysGradeByIds(ids);
        return toAjax(i);
    }

    //导出
    @PreAuthorize("@ss.hasPermi('system:grade:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGrade sysGrade){
        List<SysGrade> sysGradeList = sysGradeService.getSysGradeList(sysGrade);
        getSysGradeAvg(sysGradeList);
        //导出时添加总计
        SysGrade sysGradeSum = sysGradeService.getSysGradeSum();
        sysGradeSum.setName("总计");
        sysGradeList.add(sysGradeSum);
        ExcelUtil<SysGrade> sysGradeExcelUtil = new ExcelUtil<>(SysGrade.class);
        sysGradeExcelUtil.exportExcel(response,sysGradeList,"学生成绩");
    }

   //下载模板
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysGrade> util = new ExcelUtil<SysGrade>(SysGrade.class);
        util.importTemplateExcel(response, "学生成绩");
    }

    //导入
    @PreAuthorize("@ss.hasPermi('system:grade:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file,boolean updateSupport) throws Exception {
        ExcelUtil<SysGrade> util = new ExcelUtil<>(SysGrade.class);
        List<SysGrade> gradeList = util.importExcel(file.getInputStream());
        String message = sysGradeService.importGrade(gradeList, updateSupport, "");
        return AjaxResult.success(message);
    }

    @GetMapping("/echarts/{project}")
    public TableDataInfo echarts(@PathVariable("project") String project){
        List<SysGrade> sysGradeByProject = sysGradeService.getSysGradeByProject(project);
        return getDataTable(sysGradeByProject);
    }
}
