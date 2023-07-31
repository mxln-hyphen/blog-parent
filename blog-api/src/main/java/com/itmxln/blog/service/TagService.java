package com.itmxln.blog.service;

import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
}