package com.example.peiwen.mapper;

import com.example.peiwen.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.peiwen.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
//    根据课程id查询课程基本信息
    public CoursePublishVo getPublishCourseInfo(String courseId);
}
