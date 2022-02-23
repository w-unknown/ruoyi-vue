package com.wsq.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

public class ESTest_Index_Delete {
    public static void main(String[] args) throws IOException {

        //创建elasticsearch客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        //查询索引
        DeleteIndexRequest request = new DeleteIndexRequest("people");
        AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);
        //响应状态
        System.out.println(response.isAcknowledged());

        //关闭elasticsearch客户端
        esClient.close();
    }
}
