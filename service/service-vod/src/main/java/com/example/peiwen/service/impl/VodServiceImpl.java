package com.example.peiwen.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.example.peiwen.Utils.ConstantVodUtils;
import com.example.peiwen.Utils.InitObject;
import com.example.peiwen.service.VodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    //    上传视频到阿里云
    public String uploadAlyiVideo(MultipartFile file) {
        try {
            //        fileName：上传文件原始名称
            String fileName = file.getOriginalFilename();
            //        title：上传之后显示的名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            //        inputStream:上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET,title,fileName,inputStream);

            UploadVideoImpl upload = new UploadVideoImpl();
            UploadStreamResponse response = upload.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            }else {
                videoId = response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
//    删除多个视频
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
//            初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
//             创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
//            videoIdList转换成1,2,3,4
            String id = StringUtils.join(videoIdList.toArray(), ",");
//            向request中设置多个视频id
            request.setVideoIds(id);
//            调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
