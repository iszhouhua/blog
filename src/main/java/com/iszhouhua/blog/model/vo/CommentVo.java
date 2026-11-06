package com.iszhouhua.blog.model.vo;

import com.iszhouhua.blog.model.pojo.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentVo {
    private Long id;
    private String content;
    private Date createTime;
    private UserVo user;
    private UserVo replyUser;
    private List<CommentVo> subComments;

    public static CommentVo from(Comment comment) {
        CommentVo result = new CommentVo();
        result.setId(comment.getId());
        result.setContent(comment.getContent());
        result.setCreateTime(comment.getCreateTime());
        result.setUser(UserVo.from(comment.getUser()));
        result.setReplyUser(UserVo.from(comment.getReplyUser()));
        if (comment.getSubComments() != null) {
            result.setSubComments(comment.getSubComments().stream().map(CommentVo::from).collect(Collectors.toList()));
        }
        return result;
    }
}
