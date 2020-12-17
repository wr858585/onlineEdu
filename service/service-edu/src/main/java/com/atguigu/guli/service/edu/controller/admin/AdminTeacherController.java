package com.atguigu.guli.service.edu.controller.admin;


import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.edu.entity.Teacher;
import com.atguigu.guli.service.edu.entity.query.TeacherQuery;
import com.atguigu.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * 管理员操作讲师模块的功能
 * @author atguigu
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin    //配置允许当前模块中的接口可以被跨域访问
@Api(tags = "讲师管理模块")//描述controller的注解，会显示在swagger页面上
public class AdminTeacherController {

    @Autowired
    TeacherService teacherService;

    //7. 根据id更新讲师
    @ApiOperation("查询指定讲师")
    @PostMapping("update")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        if(b){
            return R.ok();
        }else {
            return R.error().message("数据不存在");
        }
    }

    //6. 根据id查询讲师
    @ApiOperation(value = "查询指定讲师")
    @GetMapping("get/{id}")
    public R getTeacherById(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item",teacher);
    }

    //5. 新增讲师
    @ApiOperation(value = "新增讲师")
    @PostMapping(value = "save")    //请求体：json方式提交teacher对象 --> @RequestBody
    public R saveTeacher(@RequestBody Teacher teacher){
        //设置默认值（其实可以放在service层）
        teacher.setJoinDate(new Date());
        if(StringUtils.isEmpty(teacher.getAvatar())){
            teacher.setAvatar("");//设置默认头像地址
        }
        boolean save = teacherService.save(teacher);
        if(save){
            return R.ok();
        } else {
            return R.error().message("讲师姓名已存在");
        }
    }

    //4. 带条件的分页查询 list/1/3?name=xxx&level=1
    //get请求报文没有请求体，所以需要采用pojo的方式提交请求
    @ApiOperation(value = "带条件查询讲师的分页数据")
    @GetMapping("list/{pageNum}/{pageSize}")
    public R listPage(@ApiParam(value = "页码") @PathVariable Integer pageNum,
                      @ApiParam(value = "每页记录数量") @PathVariable Integer pageSize,
                      TeacherQuery teacherQuery){
//        Page<Teacher> page = new Page<>(pageNum, pageSize);
//        page = teacherService.page(page);
        //这种复杂的业务逻辑，不要写在controller层，直接写一个方法进service层处理
        Page<Teacher> page = teacherService.selectTeacherPageByCondition(pageNum, pageSize, teacherQuery);
        return R.ok().data("page",page);
    }

    //2. 逻辑删除指定讲师的方法
    /**
     * 请求参数的方式：get方式 --> url?k=v&k2=v2 --> 通过@RequestParam("k")来接收
     * 请求体的方式：post方式 --> 请求体中以请求参数的方式提交（和get方式一样），或者请求体中以json字符串的方式提交参数（@RequestBody）
     * 路径参数的方式：/remove/1 --> @DeleteMapping("/remove/{id}"） --> 用@PathVariable("id")
     *
     * 方式的选择：
     * 1. 如果提交的请求参数较少（id），一般用路径参数的方式
     * 2. 如果参数比较多，get方式提交，一般用请求参数pojo的方式入参
     * 3. 如果不是get，是其他的方式提交，且参数较多，一般采用请求体json字符串方式提交
     *
     * 常见的响应报文的响应状态码：
     * 200：成功
     * 302：重定向
     * 400：请求参数和后台方法要求的不一致（请求报文错误）
     * 403：没有权限
     * 404：路径访问的资源不存在
     * 405：请求方式不被支持
     * 5xx：服务器内部错误
     * 503：一般访问网关时，路径没有对应的路由匹配（没有微服务能够处理本次请求）
     */
    @ApiOperation(value = "删除讲师")
    @DeleteMapping("/remove/{id}")
    public R deleteTeacher(@ApiParam(value = "讲师id", required = true) @PathVariable String id){
        boolean b = teacherService.removeById(id);
        if(b){
            return R.ok();
        } else{
            return R.error().message("数据不存在");
        }
    }

    //1. 查询所有的讲师
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("/list")
    public R list(){
        List<Teacher> teachers = teacherService.list();
        return R.ok().data("items", teachers);
    }

}

