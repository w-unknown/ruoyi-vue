package com.wsq.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ESTest_Doc_Delete_Batch {
    public static void main(String[] args) throws IOException {

        //创建elasticsearch客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        //批量插入数据
        BulkRequest request = new BulkRequest();

        request.add(new DeleteRequest("people").id("1001"));
        request.add(new DeleteRequest("people").id("1002"));
        request.add(new DeleteRequest("people").id("1003"));
        request.add(new DeleteRequest("people").id("1004"));
        request.add(new DeleteRequest("people").id("1005"));
        request.add(new DeleteRequest("people").id("1006"));
        request.add(new DeleteRequest("people").id("1007"));


        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        System.out.println(response.getTook());
        System.out.println(response.getItems());

        //关闭elasticsearch客户端
        esClient.close();
    }
}
