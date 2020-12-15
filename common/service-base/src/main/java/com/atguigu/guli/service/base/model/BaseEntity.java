package com.atguigu.guli.service.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
//@Accessors用于配置setter方法的生成结果,chain设置为true，则setter方法返回当前对象
@Accessors(chain = true)
public class BaseEntity {

    @ApiModelProperty(value = "ID")
    //value表示当前字段映射数据库表的哪个字段
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "创建时间")
    //因为驼峰命名，所以value其实不写，数据库中的gmt_create和javabean中的gmtCreate可以映射到
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
