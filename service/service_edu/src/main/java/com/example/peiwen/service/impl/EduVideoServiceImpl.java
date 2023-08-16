package com.example.peiwen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.peiwen.entity.EduVideo;
import com.example.peiwen.mapper.EduVideoMapper;
import com.example.peiwen.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    //        1根据课程id删除小节
    //    此时删除小节还没有删除视频
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
