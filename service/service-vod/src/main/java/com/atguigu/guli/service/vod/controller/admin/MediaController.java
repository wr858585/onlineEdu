package com.atguigu.guli.service.vod.controller.admin;

import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.vod.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@CrossOrigin
@RequestMapping("/admin/vod/media")
public class MediaController {

    @Autowired
    MediaService mediaService;

    //1. 上传视频到vod
    @PostMapping("/upload")
    public R upload(MultipartFile file){
        //抽取到业务层：
//        InputStream inputStream = file.getInputStream();
//        String filename = file.getOriginalFilename();
        //获取上传之后的视频id
        String videoId = mediaService.upload(file);
        return R.ok().data("videoId", videoId);
    }

    //2. 获取未加密视频的播放地址
    @GetMapping("getPlayUrl/{videoId}")
    public R getPlayUrl(@PathVariable String videoId){
        String playUrl = mediaService.getPlayUrl(videoId);
        return R.ok().data("playUrl",playUrl);  //一般用item的key描述对象，字段的话具体些吧
    }

    //3. 获取加密视频播放凭证
    @GetMapping("/getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId){
        String playAuth = mediaService.getPlayAuth(videoId);
        return R.ok().data("playAuth",playAuth);    //一般用item的key描述对象，字段的话具体些吧
    }

    //4. 删除视频
}
