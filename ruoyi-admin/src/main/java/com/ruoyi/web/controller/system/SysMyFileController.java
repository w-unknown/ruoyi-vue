package com.ruoyi.web.controller.system;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: xg
 * @Version: 1.0
 * @Description: 功能描述
 */
@RestController
@RequestMapping("file")
public class SysMyFileController {
    @PostMapping("/upload")
    public String upload(MultipartFile file){
        if(file.isEmpty()){
            return "文件为空";
        }

        try{
            File f = new File("D:/Desktop/upload/"+file.getOriginalFilename());

            //获取字节流
            byte[] arr = file.getBytes();
            file.transferTo(f);
        }catch (IOException e){

        }
        System.out.println(file);
        return "上传成功";

    }
}
