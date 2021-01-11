package com.atguigu.guli.service.sms.controller;

import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/sms")
@CrossOrigin
@RestController
public class ApiSmsController {

    @Autowired
    SmsService smsService;

    //1. 发送短信验证码
    //参数：手机号码，本次操作类型（登录？注册？这里只是涉及注册，所以不用；以后开发写好点考虑多点）
    @GetMapping("send/{mobile}")
    public R sendMessage(@PathVariable String mobile){
        smsService.sendMessage(mobile);
        return R.ok();
    }

}
