package com.itmxln.blog.service;

import com.itmxln.blog.vo.CategoryVo;
import com.itmxln.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    /**
     * 查询所有类别
     * @return
     */
    Result findAll();

    /**
     * 查询所有文章分类
     * @return
     */
    Result findALLDetail();

    /**
     * 根据id查找指定分类
     * @param id
     * @return
     */
    Result findCategoryDetailById(Long id);
}
