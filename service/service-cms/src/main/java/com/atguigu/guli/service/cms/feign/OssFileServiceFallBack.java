package com.atguigu.guli.service.cms.feign;

import com.atguigu.guli.service.base.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {
    @Override
    public R deleteFile(String filePath, String module) {
        log.warn("熔断保护");
        return R.error().message("调用超时");
    }
}
