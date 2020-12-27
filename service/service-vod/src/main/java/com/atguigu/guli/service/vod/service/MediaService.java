package com.atguigu.guli.service.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    String upload(MultipartFile file);

    String getPlayUrl(String videoId);

    String getPlayAuth(String videoId);
}
