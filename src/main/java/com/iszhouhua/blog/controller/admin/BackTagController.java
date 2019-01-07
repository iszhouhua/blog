package com.iszhouhua.blog.controller.admin;


import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签后台控制器
 * @author ZhouHua
 * @since 2018-12-01
 */
@RestController
@RequestMapping("/admin/tag")
public class BackTagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public Result list() {
        return Result.success("查询成功", tagService.list());
    }
}
