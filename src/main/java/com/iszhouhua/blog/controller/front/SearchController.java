package com.iszhouhua.blog.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.model.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台搜索控制器
 * @author ZhouHua
 * @date 2018/12/1
 */
@Controller
@RequestMapping("search")
public class SearchController extends BaseController {

    /**
     * 搜索文章
     * @param keyword  关键字
     * @return
     */
    @GetMapping("{keyword}")
    public String search(Model model,@PathVariable(value = "keyword") String keyword) {
        return search(model,keyword,1);
    }

    /**
     * 搜索页分页
     * @param keyword  关键字
     * @param pageIndex 需要加载的页码
     * @return
     */
    @GetMapping("{keyword}/{pageIndex}")
    public String search(Model model,@PathVariable(value = "keyword") String keyword,@PathVariable(value = "pageIndex") Integer pageIndex) {
        IPage<Article> page=articleService.findPageByKeyword(new Page<>(pageIndex,Const.PAGE_SIZE),keyword);
        model.addAttribute("page",page);
        return "search";
    }
}
