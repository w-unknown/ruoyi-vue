package com.wsq.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ESTest_Doc_Insert_Batch {
    public static void main(String[] args) throws IOException {

        //创建elasticsearch客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        //批量插入数据
        BulkRequest request = new BulkRequest();

        request.add(new IndexRequest("people").id("1001").source(XContentType.JSON,"name","zhangsan","age","30","sex","男"));
        request.add(new IndexRequest("people").id("1002").source(XContentType.JSON,"name","lishi","age","40","sex","女"));
        request.add(new IndexRequest("people").id("1003").source(XContentType.JSON,"name","wangwu1","age","20","sex","男"));
        request.add(new IndexRequest("people").id("1004").source(XContentType.JSON,"name","wangwu2","age","50","sex","男"));
        request.add(new IndexRequest("people").id("1005").source(XContentType.JSON,"name","wangwu3","age","30","sex","女"));
        request.add(new IndexRequest("people").id("1006").source(XContentType.JSON,"name","liuliu","age","89","sex","男"));
        request.add(new IndexRequest("people").id("1007").source(XContentType.JSON,"name","goudan","age","49","sex","男"));


        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        System.out.println(response.getTook());
        System.out.println(response.getItems());

        //关闭elasticsearch客户端
        esClient.close();
    }
}
