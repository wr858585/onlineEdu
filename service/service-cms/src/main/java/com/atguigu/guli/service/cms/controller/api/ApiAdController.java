package com.atguigu.guli.service.cms.controller.api;

import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.cms.entity.Ad;
import com.atguigu.guli.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("广告推荐")
@CrossOrigin
@RequestMapping("/api/cms/ad")
public class ApiAdController {

    @Autowired
    private AdService adService;

    @Autowired
    private RedisTemplate redisTemplate;

    //以下三个是测试redis的方法
    @PostMapping("save-test")
    public R saveAd(@RequestBody Ad ad){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("ad",ad);
        return R.ok().data("item",ad);
    }

    @GetMapping("get-test/{key}")
    public R getAd(@PathVariable String key){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Ad ad = (Ad)valueOperations.get(key);
        return R.ok().data("item",ad);
    }

    @DeleteMapping("delete-test/{key}")
    public R deleteAd(@PathVariable String key){
        Boolean delete = redisTemplate.delete(key);
        if(delete){
            return R.ok().message("删除成功");
        }else{
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据推荐位id显示广告推荐")
    @GetMapping("list/{adTypeId}")
    public R listByAdTypeId(@ApiParam(value = "推荐位id", required = true) @PathVariable String adTypeId) {

        List<Ad> ads = adService.selectByAdTypeId(adTypeId);
        return R.ok().data("items", ads);
    }

}
