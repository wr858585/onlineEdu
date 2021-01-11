package com.atguigu.guli.service.ucenter.entity.form;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api("用户注册对象")
public class RegisterForm {

    @ApiModelProperty("昵称")
    public String nickName;
    @ApiModelProperty("电话号码")
    public String mobile;
    @ApiModelProperty("密码")
    public String password;
    @ApiModelProperty("验证码")
    public String code;

}
