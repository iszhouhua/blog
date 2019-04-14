package com.iszhouhua.blog.controller.front;

import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.model.Article;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.Menu;
import com.iszhouhua.blog.service.ArticleService;
import com.iszhouhua.blog.service.CommentService;
import com.iszhouhua.blog.service.ConfigService;
import com.iszhouhua.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

/**
 * @author ZhouHua
 * @date 2018/12/19
 */
public class BaseController {

    @Autowired
    protected CommentService commentService;

    @Autowired
    protected ArticleService articleService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected ConfigService configService;

    @ModelAttribute("latestComments")
    public List<Comment> latestComments() {
        return commentService.findLatestComments(8,false);
    }

    @ModelAttribute("randomArticles")
    public List<Article> randomArticles() {
        return articleService.findRandomArticles(8);
    }

    @ModelAttribute("MENUS")
    public List<Menu> menus(){
        return menuService.findAllMenu();
    }

    @ModelAttribute("GLOBAL")
    public Map<String,String> global(){
        return configService.findAllGlobal();
    }

    /**
     * 获得分页代码
     * @param pageNum 当前页
     * @param pageCount 总页数
     * @param base 分页根目录
     * @return
     */
    String pagination(int pageNum, int pageCount, String base){
        StringBuilder pageHtml=new StringBuilder("<ul class=\"pagination pagination-gal pull-right\">");
        if(pageNum>1){
            //上一页
            pageHtml.append("<li class=\"prev\"><a href=\"").append(base).append(pageNum - 1).append("/\" title=\"上一页\">«</a></li>");
            if(pageNum> Const.PAGE_SIDE_NUM+1){
                //首页
                pageHtml.append("<li><a href=\"").append(base).append("\" title=\"首页\">1</a></li>");
            }
            if(pageNum> Const.PAGE_SIDE_NUM+2){
                pageHtml.append("<li><span>...</span></li>");
            }
        }
        //计算起始页和结束页
        int startNum=Math.max(pageNum- Const.PAGE_SIDE_NUM,1),endNum=Math.min(pageNum+ Const.PAGE_SIDE_NUM,pageCount);
        int diff=endNum-startNum,showPage=2*Const.PAGE_SIDE_NUM;
        if(pageCount>showPage && diff<showPage){
            if(startNum< Const.PAGE_SIDE_NUM){
                //延伸结束页
                endNum=Math.min(endNum+diff,pageCount);
            }else{
                //延伸起始页
                startNum=Math.max(startNum-diff,1);
            }
        }
        //循环得到中间的页码
        for (int i = startNum; i <= endNum; i++) {
            if(i==pageNum){
                pageHtml.append("<li class=\"active disabled\"><span>").append(i).append("</span></li>");
            }else{
                pageHtml.append("<li><a href=\"").append(base).append(i).append("/\">").append(i).append("</a></li>");
            }
        }
        int temp=pageCount-pageNum;
        if(temp>0){
            if(temp> Const.PAGE_SIDE_NUM+1){
                pageHtml.append("<li><span>...</span></li>");
            }
            if(temp> Const.PAGE_SIDE_NUM){
                //最后一页
                pageHtml.append("<li><a href=\"").append(base).append(pageCount).append("/\" title=\"最后一页\">").append(pageCount).append("</a></li>");
            }
            //下一页
            pageHtml.append("<li class=\"next\"><a href=\"").append(base).append(pageNum + 1).append("/\" title=\"下一页\">»</a></li>");
        }
        pageHtml.append("</ul>");
        return pageHtml.toString();
    }
}
