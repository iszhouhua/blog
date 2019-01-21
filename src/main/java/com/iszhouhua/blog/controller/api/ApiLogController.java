package com.iszhouhua.blog.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.Log;
import com.iszhouhua.blog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志管理
 * @author ZhouHua
 * @date 2019/1/16
 */
@RestController
@RequestMapping("api/log")
public class ApiLogController {
    @Autowired
    private LogService logService;

    /**
     * 获得最近一周访客数和总访客数
     * @return
     */
    @GetMapping("visitsCount")
    public Result visitsCount(){
        Map<String,Integer> data=new HashMap<>();
        int totalVisits= logService.count();
        data.put("totalVisits",totalVisits);
        int latestVisits=logService.count(new QueryWrapper<Log>().apply("create_time > DATE_SUB(CURDATE(), INTERVAL 1 WEEK)"));
        data.put("latestVisits",latestVisits);
        return Result.success("获取成功",data);
    }

    /**
     * 获得最新的n条最新日志
     * @return
     */
    @GetMapping("latest")
    public Result latest(int number){
        List<Log> logList=logService.findLatestLog(number);
        return Result.success("获取成功",logList);
    }

    /**
     * 统计访客的浏览器数据
     * @return
     */
    @GetMapping("browser")
    public Result browser(){
        return Result.success("获取成功",logService.statBrowser());
    }

    /**
     * 统计访客的操作系统
     * @return
     */
    @GetMapping("operatingSystem")
    public Result operatingSystem(){
        return Result.success("获取成功",logService.statOperatingSystem());
    }

    /**
     * 统计访客的所在城市
     * @return
     */
    @GetMapping("city")
    public Result city(){
        return Result.success("获取成功",logService.statCity());
    }
}
