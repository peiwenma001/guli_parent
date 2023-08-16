package com.example.peiwen.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.peiwen.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.peiwen.entity.vo.CourseInfoVo;
import com.example.peiwen.entity.vo.CoursePublishVo;
import com.example.peiwen.entity.vo.CourseQuery;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
public interface EduCourseService extends IService<EduCourse> {
    //    添加课程基本信息。
    String saveCourseInfo(CourseInfoVo courseInfoVo);
    //    根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);
    //    修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);
//    根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);
//      课程列表条件查询带分页
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);
    //    删除课程
    void removeCourse(String courseId);

}
