package com.itmxln.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmxln.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 根据标签热度查询最热门的标签
     * @param limit 前limit个热门标签
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
