package com.wsq.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ESTest_Doc_Get {
    public static void main(String[] args) throws IOException {

        //创建elasticsearch客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        //查询数据
        GetRequest request = new GetRequest();
        request.index("people").id("1001");


        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);

        System.out.println(response.getSource());
        System.out.println(response.getSourceAsString());

        //关闭elasticsearch客户端
        esClient.close();
    }
}
