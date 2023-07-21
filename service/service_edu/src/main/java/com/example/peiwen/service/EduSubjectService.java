package com.example.peiwen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.peiwen.entity.EduSubject;
import com.example.peiwen.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    //课程分类列表（树形）
    List<OneSubject> getAllOneTwoSubject();
}
