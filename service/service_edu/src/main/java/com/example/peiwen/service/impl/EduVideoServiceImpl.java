package com.example.peiwen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.peiwen.client.VodClient;
import com.example.peiwen.entity.EduVideo;
import com.example.peiwen.mapper.EduVideoMapper;
import com.example.peiwen.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
//    注入vod
    @Resource
    private VodClient vodClient;
    @Override
    //        1根据课程id删除小节
    //    此时删除小节还没有删除视频
    public void removeVideoByCourseId(String courseId) {
//        1根据课程id查出所有视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);
//        变成list<EduVideo>变成list<String>
        ArrayList<String> arrayList = new ArrayList<>();
        for (EduVideo s:eduVideos) {
            String videoSourceId = s.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                arrayList.add(videoSourceId);
            }
        }
        if (arrayList.size() > 0){
            vodClient.deleteBatch(arrayList);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
