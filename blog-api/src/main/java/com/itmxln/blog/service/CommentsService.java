package com.itmxln.blog.service;

import com.itmxln.blog.dao.pojo.Comment;
import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.params.CommentParam;

public interface CommentsService {

    /**
     * 根据文章id查询所有评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 将commentParam传递的数据写进数据库
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
