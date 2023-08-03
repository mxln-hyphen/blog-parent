package com.itmxln.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmxln.blog.dao.dos.Archives;
import com.itmxln.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();

    /**
     * 获取符合指定条件，分页后的文章列表
     * @param page
     * @param categoryId
     * @param tagId
     * @param year
     * @param month
     * @return
     */
    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
