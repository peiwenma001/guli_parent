package com.example.peiwen.service.impl;

import com.alibaba.excel.EasyExcel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.peiwen.Listener.SubjectExcelListener;
import com.example.peiwen.entity.EduSubject;
import com.example.peiwen.entity.excel.SubjectData;
import com.example.peiwen.entity.subject.OneSubject;
import com.example.peiwen.entity.subject.TwoSubject;
import com.example.peiwen.mapper.EduSubjectMapper;
import com.example.peiwen.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    //课程分类列表（树形）
    public List<OneSubject> getAllOneTwoSubject() {
//        1查询所有的一级分类parent_id = "0"
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> OneSubjectsList = baseMapper.selectList(wrapperOne);
//        2查询所有的二级分类parent_id ！= "0"
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id",0);
        List<EduSubject> TWOSubjectsList = baseMapper.selectList(wrapperTwo);
//        创建lis集合，用于存储数据
        ArrayList<OneSubject> finalSubjectList = new ArrayList<>();
//        3封装一级分类
        for (int i = 0;i < OneSubjectsList.size();i++) {//遍历OneSubjectsList集合
//            得到OneSubjectsList中每个EduSubject对象
            EduSubject eduSubject = OneSubjectsList.get(i);
            OneSubject oneSubject = new OneSubject();
//            对象复制，把eduSubject中的数据复制到oneSubject
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);

//        4封装二级分类
//            创建list集合封装每一个一级分类的二级分类
            ArrayList<TwoSubject> twoSubjectArrayList = new ArrayList<>();
//            遍历二级分类
            for (int j = 0; j < TWOSubjectsList.size(); j++) {
//                获取每个二级分类
                EduSubject tSubject = TWOSubjectsList.get(j);
                if (tSubject.getParentId().equals(oneSubject.getId())){ //根据二级分类ParentId判断属于那个一级分类
//            对象复制，把tSubject中的数据复制到twoSubjectArrayList
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoSubjectArrayList.add(twoSubject);
                }
            }
//            把一级分类下面的所有二级分类放到一级分类中。
            oneSubject.setChildren(twoSubjectArrayList);
        }
        return finalSubjectList;
    }
}
