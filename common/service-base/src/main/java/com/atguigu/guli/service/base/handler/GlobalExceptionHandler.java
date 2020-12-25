package com.atguigu.guli.service.base.handler;

import com.atguigu.guli.service.base.exception.GuliException;
import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.base.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.calendar.LocalGregorianCalendar;

import java.util.Date;

@ControllerAdvice
//@RestControllerAdvice
@Slf4j  //lombok的注解，放在类的上面，可以引入静态的slf4j的实例，供当前类使用
public class GlobalExceptionHandler {

    //异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exception(Exception e){    //接收的异常（实参）必须和注解中声明要处理的异常类型一致
//        e.printStackTrace();    //打印异常的堆栈信息
        log.error("异常为：${}", ExceptionUtils.getStackTrace(e));
        return R.error().message(e.getMessage());
    }

    //guli异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R exception(GuliException e){    //接收的异常（实参）必须和注解中声明要处理的异常类型一致
//        e.printStackTrace();    //打印异常的堆栈信息
        log.error("异常为：${}", ExceptionUtils.getStackTrace(e));
        return R.error().code(e.getCode()).message(e.getMessage()).success(e.getSuccess());
    }

    //数据库异常处理
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R exception(BadSqlGrammarException e){    //接收的异常（实参）必须和注解中声明要处理的异常类型一致
//        e.printStackTrace();    //打印异常的堆栈信息
        log.error("异常为：${}",  ExceptionUtils.getStackTrace(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    //Json解析异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R exception(HttpMessageNotReadableException e){    //接收的异常（实参）必须和注解中声明要处理的异常类型一致
//        e.printStackTrace();    //打印异常的堆栈信息
        log.error("异常为：${}",  ExceptionUtils.getStackTrace(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

}
