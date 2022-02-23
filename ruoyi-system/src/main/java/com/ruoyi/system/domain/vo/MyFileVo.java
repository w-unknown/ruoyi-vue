package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * @Author: xg
 * @Version: 1.0
 * @Description: 功能描述
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "t_file")
public class MyFileVo {

    @Field(name="name")
    private String name;
    @Field(name = "path")
    private String path;
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    @Field(name = "modTime")
    private Date modTime;
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    @Field(name = "creTime")
    private Date creTime;
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    @Field(name = "delTime")
    private Date delTime;
    @Field(name = "typeName")
    private String typeName;
    @Field(name = "extendName")
    private String extendName;
    @Field(name = "parentId")
    private Integer parentId;
    @Field(name = "size")
    private Integer size;
}
