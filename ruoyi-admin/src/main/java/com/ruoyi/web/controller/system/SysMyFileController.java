package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.vo.MyFileVo;
import com.ruoyi.system.service.ISysMyFileService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/file")
public class SysMyFileController extends BaseController {

    @Value("${fileUpload.path}")
    private String filePath;

    @Autowired
    private ISysMyFileService sysFileService;

    @GetMapping("/list")
    public TableDataInfo getFileVoList(MyFileVo myFileVo){
        List<MyFileVo> fileList = sysFileService.getFileVoList(myFileVo);
        System.out.println(fileList);
        return getDataTable(fileList);
    }

    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file, boolean updateSupport){
        if(file.isEmpty()){
            return AjaxResult.error("文件为空");
        }
        try{
            File f = new File(filePath+file.getOriginalFilename());

            //获取字节流
            byte[] arr = file.getBytes();
            file.transferTo(f);
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file);
        return AjaxResult.success();

    }

    /**
     * BoolQuery( ) 用于组合多个叶子或复合查询子句的默认查询
     * must 相当于 与 & =
     * must not 相当于 非 ~   ！=
     * should 相当于 或  |   or
     * filter  过滤
     */

    /*@Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @RequestMapping("/search")
    public TableDataInfo myFileVoSearch() {
        //BoolQueryBuilder用来组装查询的条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //组装年龄条件
        RangeQueryBuilder age = QueryBuilders.rangeQuery("age");
        //年龄小于20的
        RangeQueryBuilder lt = age.gte(10).lte(100);
        //组装时间条件
        RangeQueryBuilder createTime = QueryBuilders.rangeQuery("create_time");
        //组装创建时间大于等于"2020-09-16 11:40:27小于等于"2020-09-17 11:40:43"的条件
        RangeQueryBuilder timeRange = createTime
                .format("yyyy-MM-dd HH:mm:ss")
                .gte("2019-09-10 11:40:27")
                .lte("2020-09-20 11:40:43");
        //将组装完的条件用queryBuilder.must()进行组合
        BoolQueryBuilder resultQuery = queryBuilder
                .must(lt)
                .must(timeRange);
        //NativeSearchQueryBuilder用来进行查询的调整比如排序,分页等;
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        NativeSearchQuery nativeSearchQuery = builder
                .withFilter(resultQuery)//关联查询条件
                .withPageable(PageRequest.of(0, 10))//分页条件
                .withSort(SortBuilders.fieldSort("create_time.keyword").order(SortOrder.ASC))
                .build();
        SearchHits<MyFileVo> myFileVoSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery, MyFileVo.class, IndexCoordinates.of("t_file"));
        //获取得到的数据集合
        List<MyFileVo> memberInfos = myFileVoSearchHits.toList().parallelStream().map(SearchHit::getContent).collect(Collectors.toList());
        return getDataTable(memberInfos);
    }*/
}
