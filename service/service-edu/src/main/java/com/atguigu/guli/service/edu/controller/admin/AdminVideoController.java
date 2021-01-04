package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.edu.entity.Video;
import com.atguigu.guli.service.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/edu/video")
public class AdminVideoController {

    @Autowired
    VideoService videoService;

    //4. 更新课程
    @PutMapping("update-video")
    public R updateVideo(@RequestBody Video video){
        boolean b = videoService.updateById(video);
        if(b){
            return R.ok().data("item",video);
        }else {
            return R.error().message("课时不存在");
        }
    }

    //3. 查询课时，用于更新时的回显
    @GetMapping("query-video/{videoId}")
    public R queryVideo(@PathVariable String videoId){
        Video video = videoService.getById(videoId);
        if(video == null){
            return R.error().message("课时不存在");
        }else{
            return R.ok().data("item",video);
        }
    }

    //2. 删除课时
    @DeleteMapping("delete-video/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        boolean b = videoService.removeById(videoId);
        if(b){
            return R.ok().message("课时删除成功");
        }else {
            return R.error().message("课时删除失败");
        }
    }

    //1. 新增课时
    @PostMapping("save-video")
    public R saveVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok().message("新增课时成功");
    }

}

