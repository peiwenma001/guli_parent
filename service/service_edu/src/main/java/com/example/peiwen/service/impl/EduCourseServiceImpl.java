package com.example.peiwen.service.impl;

import com.example.peiwen.entity.EduCourse;
import com.example.peiwen.entity.EduCourseDescription;
import com.example.peiwen.entity.vo.CourseInfoVo;
import com.example.peiwen.mapper.EduCourseMapper;
import com.example.peiwen.service.EduCourseDescriptionService;
import com.example.peiwen.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
}
