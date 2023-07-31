package com.itmxln.blog.service.impl;

import com.itmxln.blog.dao.mapper.TagMapper;
import com.itmxln.blog.dao.pojo.Tag;
import com.itmxln.blog.service.TagService;
import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisplus无法进行多表查询
        List<Tag> tags =  tagMapper.findTagsByArticleId(articleId);
        // List<Tag> tags =  new ArrayList<>();
        return copyList(tags);
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public Result hots(int limit) {
        /**
         * 1、标签所拥有的文章数量作为标签的热度
         * 2、查询 根据tag_id 分组记数
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }
}
