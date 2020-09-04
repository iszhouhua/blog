package com.iszhouhua.blog.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.Link;
import com.iszhouhua.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 链接管理
 *
 * @author ZhouHua
 * @since 2018-04-13
 */
@RestController
@RequestMapping("api/link")
public class ApLinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("list")
    public Result list(Page<Link> page) {
        return Result.success("查询成功", linkService.page(page));
    }

    @PostMapping
    public Result save(@RequestBody Link link) {
        ValidatorUtils.validate(link);
        boolean res = linkService.save(link);
        linkService.clearCache();
        return res ? Result.success("保存成功", link) : Result.fail("保存失败");
    }

    @PutMapping
    public Result update(@RequestBody Link link) {
        ValidatorUtils.validate(link);
        if (Objects.isNull(link.getId())) {
            return Result.fail("ID不能为空");
        }
        boolean res = linkService.updateById(link);
        linkService.clearCache();
        return res ? Result.success("修改成功", link) : Result.fail("修改失败");
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success("查询成功", linkService.getById(id));
    }

    @DeleteMapping
    public Result remove(Long id) {
        boolean res = linkService.removeById(id);
        linkService.clearCache();
        return res ? Result.success("删除成功") : Result.fail("删除失败");
    }
}
