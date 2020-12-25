package com.atguigu.guli.service.edu.feign.fallback;

import com.atguigu.guli.service.base.result.R;
import com.atguigu.guli.service.base.result.ResultCodeEnum;
import com.atguigu.guli.service.edu.feign.OssFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OssFeignClientFallback implements OssFeignClient {
    @Override
    public R testFeignToOss(String path) {
        log.error("远程测试失败");
        return R.setResult(ResultCodeEnum.FEIGN_REMOTE_CALL_ERROR);
    }

    @Override
    public R deleteFile(String filePath, String module) {
        log.error("删除文件失败：path={}, module={}", filePath,module);
        return R.setResult(ResultCodeEnum.FEIGN_REMOTE_CALL_ERROR);
    }
}
