package com.example.peiwen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.peiwen.entity.EduTeacher;
import com.example.peiwen.entity.vo.TeacherQuery;
import com.example.peiwen.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import peiwen.commonutils.R;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author peiwen
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/eduservice/teacher")
@Api(tags = "讲师管理")
@CrossOrigin
public class EduTeacherController {
  @Resource
    EduTeacherService eduTeacherService;
  //查询表中所有数据
    //rest风格
    @GetMapping("findAllTeacher")
    @ApiOperation(value = "所有讲师列表")
    public R findAllTeacher(){
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return R.ok().data("items",teacherList);
    }
    //逻辑删除讲师方法
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id逻辑删除讲师")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
        boolean removeById = eduTeacherService.removeById(id);
        if (removeById == true){
           return R.ok();
        }else {
           return R.error();
        }
    }
    //讲师分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation(value = "讲师分页")
    //current参数为当前页，limit为每页的记录数
    public R pageTeacher(@ApiParam(name = "current",value = "当前页",required = true) @PathVariable long current,
                         @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        //调用方法实现分页
        eduTeacherService.page(page,null);//调用方法时，底层把所有分页数据封装到了page对象里面
        long total = page.getTotal();//总记录数
        List<EduTeacher> records = page.getRecords();//每页数据的list集合
        return R.ok().data("total",total).data("records",records);
    }
    //条件查询带分页
    @ApiOperation(value = "讲师查询带分页")
    @PostMapping ("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current",value = "当前页",required = true)@PathVariable long current,
                                  @ApiParam(name = "limit",value = "每页记录数",required = true)@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //构建条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);//这几个判断方法column属性不是entity属性名称，而是数据库表中字段名称
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        //排序
        wrapper.orderByDesc("gmt_modified");
        //调用方法实现条件查询带分页功能
        eduTeacherService.page(page,wrapper);
        long total = page.getTotal();//总记录数
        List<EduTeacher> records = page.getRecords();//每页数据的list集合
        return R.ok().data("total",total).data("records",records);
    }
    //添加讲师
    @PostMapping("addTeacher")
    @ApiOperation(value = "讲师添加")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save == true){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //修改讲师
    //第一步根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    @ApiOperation(value = "根据id查询讲师")
    public R getTeacher(@PathVariable String id){
        EduTeacher teacherid = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacherid);
    }
    //第二步修改讲师
    @PostMapping ("updateTeacher")
    @ApiOperation(value = "根据id修改讲师")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean updateById = eduTeacherService.updateById(teacher);
        if (updateById == true){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

