package com.example.peiwen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.peiwen.entity.EduChapter;
import com.example.peiwen.entity.EduVideo;
import com.example.peiwen.entity.chater.ChaterVo;
import com.example.peiwen.entity.chater.VideoVo;
import com.example.peiwen.mapper.EduChapterMapper;
import com.example.peiwen.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.peiwen.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author peiwen
 * @since 2023-07-21
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Resource
    private EduVideoService videoService;
    @Override
    public List<ChaterVo> getChapterVideoByCourseId(String courseId) {

//        1根据课程id查出课程中所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
//        course_id此名称对应数据库字段名称
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
//        2根据课程id查出课程中所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
//        创建list集合用于封装数据
        ArrayList<ChaterVo> finalList = new ArrayList<>();
//        3遍历查询章节的list集合进行封装
//        遍历list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
//            每个章节
            EduChapter eduChapter = eduChapterList.get(i);
//            eduChapter对象值复制到ChaterVo中去
            ChaterVo chaterVo = new ChaterVo();
            BeanUtils.copyProperties(eduChapter,chaterVo);
//            把chaterVo放到最终list集合中
            finalList.add(chaterVo);

//            创建集合用于封装章节中的小节
            ArrayList<VideoVo> videoVoList = new ArrayList<>();
            //        4遍历查询小节的list集合进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
//                等到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
//                判断小节chapterid和章节里面的id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
//                    封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
//                    放到小节封装集合
                    videoVoList.add(videoVo);
                }
            }
//            把封装之后的list集合放到章节的对象中去
            chaterVo.setChildren(videoVoList);
        }
        return finalList;
    }

    @Override
    //    删除章节
    public void deleteChapter(String chapterId) {
//        根据chapterid章节id 查询小节表（edu_video），如果返回数据不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        //        chapter_id此名称对应数据库字段名称
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
//        判断小节表中是否有小节
        if (count>0){
            throw new RuntimeException("删除失败");
        }else {
            baseMapper.deleteById(chapterId);
        }
    }

    @Override
    //        2根据课程id删除章节
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
