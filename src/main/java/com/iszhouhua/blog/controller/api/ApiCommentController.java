package com.iszhouhua.blog.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.IPUtils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.model.enums.CommentStatusEnum;
import com.iszhouhua.blog.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论管理
 * @author ZhouHua
 * @since 2018-12-01
 */
@RestController
@RequestMapping("api/comment")
public class ApiCommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public Result list(Page<Comment> page, String content, Integer status) {
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        if(status==null){
            queryWrapper.ne("a.status",2);
        }else{
            queryWrapper.eq("a.status",status);
        }
        if(StringUtils.isNotBlank(content)){
            queryWrapper.like("a.content",content);
        }
        IPage<Comment> commentPage=commentService.findCommentsByPage(page,queryWrapper);
        return Result.success("查询成功",commentPage);
    }

    @PostMapping
    public Result save(@RequestBody Comment comment, HttpServletRequest request){
        if(comment.getId()==null||comment.getId()==0){
            User user=(User)request.getSession().getAttribute(Const.USER_SESSION_KEY);
            comment.setAdmin(true);
            comment.setAuthor(user.getNickname());
            comment.setEmail(user.getEmail());
            comment.setEmailMd5(user.getEmailMd5());
            comment.setUserAgent(request.getHeader("user-agent"));
            comment.setIp(IPUtils.getIpAddr(request));
            comment.setStatus(CommentStatusEnum.PUBLISHED.getValue());
            commentService.save(comment);
        }else{
            commentService.updateById(comment);
        }
        return Result.success("操作成功",comment);
    }

    @PutMapping
    public Result info(Long id){
        return Result.success("查询成功",commentService.findCommentById(id));
    }

    /**
     * 获得最近一周评论数和总评论数
     * @return
     */
    @GetMapping("commentCount")
    public Result commentCount(){
        Map<String,Integer> data=new HashMap<>();
        int totalComment= commentService.count();
        data.put("totalComment",totalComment);
        int latestComment=commentService.count(new QueryWrapper<Comment>().apply("create_time > DATE_SUB(CURDATE(), INTERVAL 1 WEEK)"));
        data.put("latestComment",latestComment);
        return Result.success("获取成功",data);
    }

    /**
     * 获得最新的n条评论
     * @param number 获取的数量
     * @return
     */
    @GetMapping("latest")
    public Result latest(int number) {
        List<Comment> commentList=commentService.findLatestComments(number,true);
        return Result.success("查询成功",commentList);
    }
}
