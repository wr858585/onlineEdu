package com.atguigu.guli.service.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CoursePublishVo {

    private String id;

    @ApiModelProperty(value = "课程讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "课程专业标题")
    private String subjectTitle;

    @ApiModelProperty(value = "课程专业父级标题")
    private String subjectParentTitle;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

}
