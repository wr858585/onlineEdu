package com.atguigu.guli.service.cms.feign;

import com.atguigu.guli.service.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-oss", fallback = OssFileServiceFallBack.class)
@Service
public interface OssFileService {

    @DeleteMapping("/admin/oss/file/delete")
    public R deleteFile(@RequestParam String filePath, @RequestParam String module);

}
