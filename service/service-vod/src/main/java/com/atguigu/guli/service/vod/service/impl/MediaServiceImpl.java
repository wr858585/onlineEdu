package com.atguigu.guli.service.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.utils.StringUtils;
import com.atguigu.guli.service.base.exception.GuliException;
import com.atguigu.guli.service.base.result.ResultCodeEnum;
import com.atguigu.guli.service.vod.config.VodProperties;
import com.atguigu.guli.service.vod.service.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class MediaServiceImpl implements MediaService {

    @Autowired
    private VodProperties vodProperties;

    @Override
    public String upload(MultipartFile file, String originalFilename) {

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

        UploadStreamRequest request = new UploadStreamRequest(
                vodProperties.getAccessKeyId(),
                vodProperties.getAccessKeySecret(),
                title, originalFilename, inputStream);

        /* 模板组ID(可选) */
        request.setTemplateGroupId(vodProperties.getTemplateGroupId());
        /* 工作流ID(可选) */
        request.setWorkflowId(vodProperties.getWorkFlowId());

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        String videoId = response.getVideoId();
        //没有正确的返回videoid则说明上传失败
        if(StringUtils.isEmpty(videoId)){
            log.error("阿里云上传失败：" + response.getCode() + " - " + response.getMessage());
            throw new GuliException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
        }

        return videoId;
    }

    @Override
    public String getPlayUrl(String videoId) {
        return null;
    }

    @Override
    public String getPlayAuth(String videoId) {
        return null;
    }
}
