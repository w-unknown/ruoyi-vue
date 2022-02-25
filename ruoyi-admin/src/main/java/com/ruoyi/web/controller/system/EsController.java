package com.ruoyi.web.controller.system;

/**
 * @Author: xg
 * @Version: 1.0
 * @Description: 功能描述
 */
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.vo.MyFileVo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("system/file")
@RestController
public class EsController extends BaseController {

    /**
     * BoolQuery( ) 用于组合多个叶子或复合查询子句的默认查询
     * must 相当于 与 & =
     * must not 相当于 非 ~   ！=
     * should 相当于 或  |   or
     * filter  过滤
     */

    @Autowired
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
    }
}
