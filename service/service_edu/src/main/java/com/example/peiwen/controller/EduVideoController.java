package com.example.peiwen.controller;


import com.example.peiwen.client.VodClient;
import com.example.peiwen.entity.EduVideo;
import com.example.peiwen.service.EduVideoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import peiwen.commonutils.R;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Resource
    private EduVideoService videoService;
//    注入VodClient
    @Resource
    private VodClient vodClient;
//    添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
//    删除小节
//    后面里面会有视频，后面完善
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
//        根据小节id获取视频id
        EduVideo eduVideo = videoService.getById(id);

//        判断小节中是否有视频id
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            //        删除小节，会把阿里云中的视频删掉
            vodClient.removeAlyVideo(videoSourceId);
        }
        videoService.removeById(id);
        return R.ok();
    }
//    修改小节
    @PostMapping("updateVideo")
    public R putVideo(@RequestBody EduVideo eduVideo){
       videoService.updateById(eduVideo);
        return R.ok();
    }
//    根据id查询小节
    @GetMapping("{id}")
    public R getOneVideo(@PathVariable String id){
        EduVideo OneVideo = videoService.getById(id);
        return R.ok().data("OneVideo",OneVideo);
    }
}

