package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.base.result.ResultCodeEnum;
import com.atguigu.guli.service.edu.entity.Chapter;
import com.atguigu.guli.service.edu.entity.vo.ChapterVo;
import com.atguigu.guli.service.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/admin/edu/chapter")
@CrossOrigin
public class AdminChapterController {

    @Autowired
    ChapterService chapterService;

    //5. 根据章节id更新章节
    @PutMapping("/updateChapter")
    public R updateChapter(@RequestBody Chapter chapter){
        boolean b = chapterService.updateById(chapter);
        if(b){
            return R.ok().message("更新章节成功");
        }else{
            return R.error().message("更新章节失败");
        }
    }

    //4. 查询选中的章节（用于更新时的回显）
    @GetMapping("/getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
//        ChapterVo chapterVo = chapterService.getChapterVoById(chapterId);
        Chapter chapter = chapterService.getById(chapterId);
        if(chapter != null){
            return R.ok().data("item", chapter);
        }else {
            return R.error().message("数据不存在");
        }
    }

    //3. 删除选中的章节
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean b = chapterService.removeById(chapterId);
        if(b){
            return R.ok().message("删除成功");
        }else {
            return R.error().message("数据不存在");
        }
    }

    //2. 根据课程id查询章节和其课时的嵌套集合
    @GetMapping("/chapterNestedList/{courseId}")
    public R chapterNestedList(@PathVariable String courseId){
        List<ChapterVo> chapterVos = chapterService.chapterNestedList(courseId);
        return R.ok().data("items",chapterVos);
    }

    //1. 新增章节
    @PostMapping("/save-chapter")
    public R saveChapter(@RequestBody Chapter chapter){  //如果使用form类接收，则还需要转换成数据库表对应的对象才能保存
        boolean b = chapterService.save(chapter);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

