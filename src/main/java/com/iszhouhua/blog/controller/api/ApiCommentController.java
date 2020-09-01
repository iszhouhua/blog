package com.iszhouhua.blog.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iszhouhua.blog.common.constant.CodeEnum;
import com.iszhouhua.blog.common.constant.ConfigConst;
import com.iszhouhua.blog.common.constant.Const;
import com.iszhouhua.blog.common.util.IPUtils;
import com.iszhouhua.blog.common.util.Result;
import com.iszhouhua.blog.common.util.ValidatorUtils;
import com.iszhouhua.blog.model.Comment;
import com.iszhouhua.blog.model.User;
import com.iszhouhua.blog.model.enums.CommentStatusEnum;
import com.iszhouhua.blog.model.enums.CommentTargetTypeEnum;
import com.iszhouhua.blog.service.CommentService;
import com.iszhouhua.blog.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 评论管理
 *
 * @author ZhouHua
 * @since 2018-12-01
 */
@RestController
@RequestMapping("api/comment")
public class ApiCommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ConfigService configService;

    @GetMapping("list")
    public Result list(Page<Comment> page, Comment comment) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>(comment);
        String content = comment.getContent();
        comment.setContent(null);
        if (StringUtils.isNotBlank(content)) {
            queryWrapper.like("content", content);
        }
        if (Objects.isNull(comment.getStatus())) {
            queryWrapper.ne("status", 2);
        }
        IPage<Comment> commentPage = commentService.findCommentsByPage(page, queryWrapper);
        return Result.success("查询成功", commentPage);
    }

    /**
     * 加载更多评论
     *
     * @param current   需要加载的页数
     * @param size      需要加载的数量
     * @param articleId 文章ID
     * @return
     */
    @GetMapping("more")
    public List<Comment> commentPage(int current, int size, long articleId) {
        return commentService.findPageByArticleId(new Page<>(current, size, false), articleId).getRecords();
    }

    @PostMapping(value = {"", "save"})
    public Result save(@RequestBody Comment comment, HttpServletRequest request) {
        ValidatorUtils.validate(comment);
        if (comment.getTargetType().equals(CommentTargetTypeEnum.COMMENT.getValue())) {
            if (Objects.isNull(comment.getParentId())) {
                Comment targetComment = commentService.getById(comment.getTargetId());
                comment.setReplyUserId(targetComment.getUserId());
            } else {
                Comment parentComment = commentService.getById(comment.getParentId());
                comment.setReplyUserId(parentComment.getUserId());
            }
        }
        Boolean isCheck = configService.getConfigObject(ConfigConst.COMMENT_CHECK, Boolean.class);
        comment.setStatus(isCheck ? CommentStatusEnum.CHECKING.getValue() : CommentStatusEnum.PUBLISHED.getValue());
        User user = (User) request.getSession().getAttribute(Const.USER_SESSION_KEY);
        comment.setUserId(user.getId());
        comment.setUserAgent(request.getHeader("user-agent"));
        comment.setIp(IPUtils.getIpAddr(request));
        commentService.save(comment);
        commentService.clearCache();
        return Result.success("添加成功", comment);
    }

    @PutMapping
    public Result update(@RequestBody Comment comment) {
        if (Objects.isNull(comment.getId())) {
            return new Result(CodeEnum.VALIDATION_ERROR.getValue(), "评论ID不能为空");
        }
        boolean res = commentService.updateById(comment);
        commentService.clearCache();
        return res ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @GetMapping
    public Result info(Long id) {
        return Result.success("查询成功", commentService.findCommentById(id));
    }

    /**
     * 获得最近一周评论数和总评论数
     *
     * @return
     */
    @GetMapping("commentCount")
    public Result commentCount() {
        Map<String, Integer> data = new HashMap<>();
        int totalComment = commentService.count();
        data.put("totalComment", totalComment);
        int latestComment = commentService.count(new QueryWrapper<Comment>().apply("create_time > DATE_SUB(CURDATE(), INTERVAL 1 WEEK)"));
        data.put("latestComment", latestComment);
        return Result.success("获取成功", data);
    }

    /**
     * 获得最新的n条评论
     *
     * @param number 获取的数量
     * @return
     */
    @GetMapping("latest")
    public Result latest(int number) {
        List<Comment> commentList = commentService.findLatestComments(number, true);
        return Result.success("查询成功", commentList);
    }
}
