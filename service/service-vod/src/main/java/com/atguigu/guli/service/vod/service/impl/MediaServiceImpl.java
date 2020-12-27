package com.atguigu.guli.service.vod.service.impl;

import com.atguigu.guli.service.vod.service.MediaService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaServiceImpl implements MediaService {
    @Override
    public String upload(MultipartFile file) {
        return null;
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
