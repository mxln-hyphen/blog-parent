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
}
