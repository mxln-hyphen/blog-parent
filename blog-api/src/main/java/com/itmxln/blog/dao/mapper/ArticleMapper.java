package com.itmxln.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmxln.blog.dao.dos.Archives;
import com.itmxln.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();
}
