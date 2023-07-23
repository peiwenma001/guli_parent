package com.example.peiwen.controller;


import com.example.peiwen.entity.EduChapter;
import com.example.peiwen.entity.chater.ChaterVo;
import com.example.peiwen.service.EduChapterService;
import org.springframework.web.bind.annotation.*;

import peiwen.commonutils.R;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
@RestController
@RequestMapping("eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Resource
    private EduChapterService eduChapterService;
//    返回课程大纲列表
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChaterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }
//    添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }
//    修改
//    根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }
//    修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }
//    删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        eduChapterService.deleteChapter(chapterId);
        return R.ok();
    }
}

