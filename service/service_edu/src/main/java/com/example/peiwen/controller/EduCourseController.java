package com.example.peiwen.controller;


import com.example.peiwen.entity.vo.CourseInfoVo;
import com.example.peiwen.service.EduCourseService;
import org.springframework.web.bind.annotation.*;

import peiwen.commonutils.R;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Resource
    private EduCourseService courseService;
//    添加课程基本信息。
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id =courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
}

