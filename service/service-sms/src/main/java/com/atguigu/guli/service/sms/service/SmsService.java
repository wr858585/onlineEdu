package com.atguigu.guli.service.sms.service;

import com.aliyuncs.exceptions.ClientException;

public interface SmsService {

    void sendMessage(String mobile);

}
