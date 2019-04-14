package com.iszhouhua.blog.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.model.enums.ConfigNameEnum;
import com.iszhouhua.blog.common.util.IPUtils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.enums.CommentStatusEnum;
import com.iszhouhua.blog.service.CommentService;
import com.iszhouhua.blog.service.ConfigService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 前台评论控制器
 * @author ZhouHua
 * @date 2018/12/25
 */
@RestController("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ConfigService configService;

    /**
     * 加载评论
     * @param current 需要加载的页数
     * @param size 需要加载的数量
     * @param articleId 文章ID
     * @return
     */
    @PostMapping("more")
    public List<Comment> commentPage(int current, int size, long articleId){
        return commentService.findPageByArticleId(new Page<>(current,size,false),articleId).getRecords();
    }

    /**
     * 评论文章
     * @param comment
     * @return
     */
    @PostMapping("article")
    public Result commentArticle(Comment comment, HttpServletRequest request){
        if(StringUtils.isBlank(comment.getContent())){
            return Result.fail("请输入评论内容");
        }
        if(StringUtils.isBlank(comment.getAuthor())){
            comment.setAuthor("匿名用户");
        }
        if(StringUtils.isNotBlank(comment.getEmail())){
            comment.setEmailMd5(DigestUtils.md5Hex(comment.getEmail()));
        }
        comment.setUserAgent(request.getHeader("user-agent"));
        String ip=IPUtils.getIpAddr(request);
        comment.setIp(ip);
        comment.setAdmin(false);
        if(Boolean.parseBoolean(configService.findByName(ConfigNameEnum.COMMENT_CHECK.name()))){
            comment.setStatus(CommentStatusEnum.CHECKING.getValue());
        }else{
            comment.setStatus(CommentStatusEnum.PUBLISHED.getValue());
        }
        return commentService.save(comment)?Result.success("评论成功"):Result.fail("评论失败");
    }
}
