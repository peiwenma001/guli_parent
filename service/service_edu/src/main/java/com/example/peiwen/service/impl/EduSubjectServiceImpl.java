package com.example.peiwen.service.impl;

import com.alibaba.excel.EasyExcel;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.peiwen.Listener.SubjectExcelListener;
import com.example.peiwen.entity.EduSubject;
import com.example.peiwen.entity.excel.SubjectData;
import com.example.peiwen.mapper.EduSubjectMapper;
import com.example.peiwen.service.EduSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
