package com.example.peiwen.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    //    上传视频到阿里云
    String uploadAlyiVideo(MultipartFile file);
//删除多个视频
    void removeMoreAlyVideo(List<String> videoIdList);
}
