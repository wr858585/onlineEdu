package com.atguigu.guli.service.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.guli.common.util.FormUtils;
import com.atguigu.guli.common.util.RandomUtils;
import com.atguigu.guli.service.base.exception.GuliException;
import com.atguigu.guli.service.base.result.ResultCodeEnum;
import com.atguigu.guli.service.sms.service.SmsService;
import com.atguigu.guli.service.sms.util.Smsproperties;
import com.google.gson.Gson;
import com.netflix.client.IClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    Smsproperties smsProperties;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void sendMessage(String mobile){
        //1. 验证手机格式（正则）
        boolean b = FormUtils.isMobile(mobile);
        if(!b){
            //手机号码格式错误
            throw new GuliException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }
        String redisCodeKey = "sms:"+mobile+":code";//redis命名规范：key可以用：连接多个单词，可以作为目录层级划分到redis中
        //去redis中验证是否存在验证码
        Object redisCode = redisTemplate.opsForValue().get(redisCodeKey);
        if(!StringUtils.isEmpty(redisCode)) {
            throw new GuliException(ResultCodeEnum.SMS_SEND_ERROR);
        }
        //生成验证码
        String code = RandomUtils.getFourBitRandom();
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsProperties.getKeyId(), smsProperties.getKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", smsProperties.getRegionId());
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", smsProperties.getSignName());
        request.putQueryParameter("TemplateCode", smsProperties.getTemplateCode());
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("code",code);
        Gson gson = new Gson();
        String codeParamsJsonStr = gson.toJson(paramsMap);
        request.putQueryParameter("TemplateParam",codeParamsJsonStr);
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map dataMap = gson.fromJson(data, Map.class);
            if("".equals(dataMap.get("Code").toString())){
                log.error("发送短信的响应：" + data);
                throw new RuntimeException();
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(mobile,code,10, TimeUnit.MINUTES);
        } catch (ClientException e) {
//            e.printStackTrace();
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GuliException(ResultCodeEnum.SMS_SEND_ERROR);
        }

        //2. 调用业务方法，发送验证码
        //2.1 判断该手机号码在redis中是否存在未过期的验证码
        //2.2 判断该手机号24小时内获取验证码的次数是否超过限制
        //2.3 判断该手机号码1分钟内是否已经获得过验证码-->防止表单重复提交
        //2.4 生产验证码
        //2.5 调用阿里云api发送验证码（给用户一份）
        //2.6 将验证码存到redis中一份
    }
}
