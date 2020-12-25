package com.atguigu.guli.service.base.exception;

import com.atguigu.guli.service.base.result.ResultCodeEnum;

public class GuliException extends RuntimeException {

    private Integer code;
    private Boolean success;
    private String message;

    public Integer getCode() {
        return code;
    }

    public Boolean getSuccess() {
        return success;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //通过我们的自定义异常类，可以告诉前端本次错误的原因
    public GuliException(ResultCodeEnum codeEnum){
        super(codeEnum.getMessage());//将异常信息交给异常对象的message属性
        this.success = codeEnum.getSuccess();
        this.message = codeEnum.getMessage();
        this.code = codeEnum.getCode();
    }

    public GuliException(Boolean success, String message, Integer code){
        this.code = code;
        this.message = message;
        this.success = success;
    }

}
