package com.example.peiwen.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import peiwen.commonutils.R;

import java.util.List;

@Component
@FeignClient("service-vod")
public interface VodClient {
//    定义需要调用方法路径
        @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
//   根据视频id删除视频
        public R removeAlyVideo(@PathVariable("id") String id);
//        定义删除多个视频的方法
        @DeleteMapping("/eduvod/video/delete-batch")
        public R deleteBatch(@PathVariable("videoIdList") List<String> videoIdList);
}
