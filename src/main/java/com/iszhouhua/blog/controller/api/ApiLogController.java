package com.iszhouhua.blog.controller.api;

import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.pojo.Log;
import com.iszhouhua.blog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志管理
 *
 * @author ZhouHua
 * @date 2019/1/16
 */
@RestController
@RequestMapping("api/log")
public class ApiLogController {
    @Autowired
    private LogService logService;

    /**
     * 获得最新的n条最新日志
     *
     * @return
     */
    @GetMapping("latest")
    public Result latest(int number) {
        List<Log> logList = logService.findLatestLog(number);
        return Result.success(logList);
    }
}
