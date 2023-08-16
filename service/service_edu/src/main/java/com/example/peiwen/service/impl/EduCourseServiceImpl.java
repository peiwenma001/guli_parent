package com.example.peiwen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.peiwen.entity.EduCourse;
import com.example.peiwen.entity.EduCourseDescription;
import com.example.peiwen.entity.vo.CourseInfoVo;
import com.example.peiwen.entity.vo.CoursePublishVo;
import com.example.peiwen.entity.vo.CourseQuery;
import com.example.peiwen.mapper.EduCourseMapper;
import com.example.peiwen.service.EduChapterService;
import com.example.peiwen.service.EduCourseDescriptionService;
import com.example.peiwen.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.peiwen.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //    添加课程基本信息
    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Resource
    private EduVideoService eduVideoService;
    @Resource
    private EduChapterService eduChapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
//        1向课程表添加课程基本信息
//        CourseInfoVo对象转换为EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        baseMapper.insert(eduCourse);
//        id为自动生成，两张表id不同
        String eduCourseId = eduCourse.getId();
//        2向课程简介表添加简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
//        手动同步id
        eduCourseDescription.setId(eduCourseId);
        eduCourseDescriptionService.save(eduCourseDescription);
//        返回id给前端。
        return eduCourseDescription.getId();
    }

    @Override
    //    根据课程id查询课程基本信息
    public CourseInfoVo getCourseInfo(String courseId) {
//        1先查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
//        2查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    //    修改课程信息
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
//        1先修改课程表
        baseMapper.updateById(eduCourse);
//        2修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    //    根据课程id查询课程确认信息
    public CoursePublishVo publishCourseInfo(String id) {
//        调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    //      课程列表条件查询带分页
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    //    删除课程
    public void removeCourse(String courseId) {
//        1根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
//        2根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
//        3根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);
//        4根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
    }
}
