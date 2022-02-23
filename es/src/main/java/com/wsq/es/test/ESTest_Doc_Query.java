package com.wsq.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.swing.text.Highlighter;
import java.io.IOException;

public class ESTest_Doc_Query {
    public static void main(String[] args) throws IOException {

        //创建elasticsearch客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        //1、查询索引中全部的数据
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/

        //2、条件查询
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age",30)));

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //3、分页查询
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //偏移量
        builder.from(0);
        //每页查询条数
        builder.size(2);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //4、查询排序
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        //排序
        builder.sort("age", SortOrder.ASC);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //5、过滤条件
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        //
        String[] excludes={"age"};
        String[] includes={};
        builder.fetchSource(includes,excludes);

        //排序
        builder.sort("age", SortOrder.ASC);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //6、组合查询
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

//        boolQueryBuilder.must(QueryBuilders.matchQuery("age","30"));
//        boolQueryBuilder.must(QueryBuilders.matchQuery("sex","男"));
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex","男"));

        boolQueryBuilder.should(QueryBuilders.matchQuery("age","30"));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age","40"));

        builder.query(boolQueryBuilder);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //7、范围查询
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");

        rangeQuery.gte(30);
        rangeQuery.lte(50);

        builder.query(rangeQuery);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //8、模糊查询
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //Fuzziness.ONE：表示差一个字符
        builder.query(QueryBuilders.fuzzyQuery("name","wangwu").fuzziness(Fuzziness.ONE));
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //9、高亮查询
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan");

        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");

        builder.highlighter(highlightBuilder);
        builder.query(termQueryBuilder);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //10、聚合查询
        /*SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //求最大值
        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        builder.aggregation(aggregationBuilder);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }*/
        //11、分组查询
        SearchRequest request = new SearchRequest();
        request.indices("people");

        //查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //求最大值
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        builder.aggregation(aggregationBuilder);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }

        //关闭elasticsearch客户端
        esClient.close();
    }
}
