package com.example.peiwen.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.example.peiwen.Utils.ConstantVodUtils;
import com.example.peiwen.Utils.InitObject;
import com.example.peiwen.service.VodService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import peiwen.commonutils.R;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Resource
    private VodService vodService;
    @PostMapping("uploadAlyiVideo")
    //    上传视频到阿里云
//    MultipartFile得到上传文件
    public R uploadAlyiVideo(MultipartFile file){
//        返回上传视频id
        String videoId = vodService.uploadAlyiVideo(file);
        return R.ok().data("videoId",videoId);
    }
    @DeleteMapping("removeAlyVideo/{id}")
    //   根据视频id删除视频
    public R removeAlyVideo(@PathVariable String id){
        try {
//            初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
//             创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
//            向request中设置视频id
            request.setVideoIds(id);
//            调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.ok();
    }
//    删除多个阿里云视频的方法
//    参数:多个视频id List videoIdList
    @DeleteMapping("delete-batch")
    public R deleteBatch(List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }
}
