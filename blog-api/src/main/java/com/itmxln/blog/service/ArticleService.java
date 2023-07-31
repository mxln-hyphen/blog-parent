package com.itmxln.blog.service;

import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.params.PageParams;
import org.springframework.web.bind.annotation.RequestBody;

public interface ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     */
    Result listArchives();
}
