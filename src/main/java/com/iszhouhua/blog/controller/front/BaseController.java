package com.iszhouhua.blog.controller.front;

import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.service.ArticleService;
import com.iszhouhua.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhouHua
 * @date 2018/12/19
 */
public class BaseController {

    @Autowired
    protected CommentService commentService;

    @Autowired
    protected ArticleService articleService;

    /**
     * 前台controller共用数据
     * @param model
     */
    @ModelAttribute
    public void addAttributes(HttpServletRequest model) {
        model.setAttribute("latestComments", commentService.findLatestComments(8,false));
        model.setAttribute("randomArticles", articleService.findRandomArticles(8));
    }

    /**
     * 未找到数据，重定向到404页面
     * @return
     */
    String notFound(){
        return "redirect:404.html";
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
