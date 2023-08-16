package com.example.peiwen.service;

import com.example.peiwen.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
public interface EduVideoService extends IService<EduVideo> {
    //        1根据课程id删除小节
    void removeVideoByCourseId(String courseId);
}
