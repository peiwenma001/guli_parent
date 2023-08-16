package com.example.peiwen.service;

import com.example.peiwen.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.peiwen.entity.chater.ChaterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
public interface EduChapterService extends IService<EduChapter> {
    //    返回课程大纲列表
    List<ChaterVo> getChapterVideoByCourseId(String courseId);
    //    删除章节
    void deleteChapter(String chapterId);
    //        2根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
