package com.itmxln.blog.service;

import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 查询所有文章标签
     * @return
     */
    Result findAll();

    /**
     * 查询所有文章标签
     * @return
     */
    Result findAllDetail();

    /**
     * 根据id查询指定文章标签
     * @param id
     * @return
     */
    Result findTagsDetailById(Long id);
}
