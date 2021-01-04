package com.atguigu.guli.service.edu.entity.vo;

import com.atguigu.guli.service.edu.entity.Video;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    //video信息：chapter和video是一对多的关系，直接封装到children

//    private String id;
//
//    @ApiModelProperty(value = "节点名称")
//    private String title;
//
//    @ApiModelProperty(value = "云端视频资源")
//    private String videoSourceId;
//
//    private Boolean free;

    private List<Video> children;

}
