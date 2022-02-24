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
 * 文件和文件夹信息工具类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "t_file")
public class MyFileVo {

    /**
     * 编号
     */
    @Field(name = "id")
    private String id;
    /**
     * 文件名
     */
    @Field(name="name")
    private String name;
    /**
     * 文件路径
     */
    @Field(name = "path")
    private String path;
    /**
     * 文件状态（1为正常，0为删除）
     */
    @Field(name="status")
    private String status;
    /**
     * 上级目录
     */
    @Field(name = "parentId")
    private Integer parentId;
    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    @Field(name = "modTime")
    private Date modTime;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    @Field(name = "creTime")
    private Date creTime;
    /**
     * 删除日期
     */
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    @Field(name = "delTime")
    private Date delTime;
    /**
     * 扩展名
     */
    @Field(name = "extendName")
    private String extendName;
    /**
     * 类型名
     */
    @Field(name = "typeName")
    private String typeName;
    /**
     * 文件大小
     */
    @Field(name = "size")
    private Integer size;
}