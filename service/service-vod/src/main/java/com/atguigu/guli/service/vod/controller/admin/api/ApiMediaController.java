package com.atguigu.guli.service.vod.controller.admin.api;

import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vod/media")
@CrossOrigin
@Slf4j
@Api("阿里云视频点播")
public class ApiMediaController {

    @Autowired
    VideoService videoService;

    @GetMapping("get-play-auth/{videoSourceId}")
    public R getPlayAuth(@ApiParam(value = "阿里云视频文件的id", required = true) @PathVariable String videoSourceId){
        String playAuth = videoService.getPlayAuth(videoSourceId);
        return R.ok().data("playAuth",playAuth).message("获取播放凭证成功");
    }

}
