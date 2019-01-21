package com.iszhouhua.blog.controller.api;


import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.Tag;
import com.iszhouhua.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 标签管理
 * @author ZhouHua
 * @since 2018-12-01
 */
@RestController
@RequestMapping("api/tag")
public class ApiTagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public Result list() {
        return Result.success("查询成功", tagService.list());
    }

    @PostMapping
    public Result save(@RequestBody Tag tag){
        ValidatorUtils.validate(tag);
        tagService.saveOrUpdate(tag);
        return Result.success("保存成功",tag);
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",tagService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id){
        return tagService.removeById(id)?Result.success("删除成功"):Result.fail("删除失败");
    }
}
