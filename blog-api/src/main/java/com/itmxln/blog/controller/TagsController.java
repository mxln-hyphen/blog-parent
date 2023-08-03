package com.itmxln.blog.controller;

import com.itmxln.blog.service.TagService;
import com.itmxln.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public Result hot(){
        int limit = 6;  //查询标签数量
        return tagService.hots(limit);
    }

    @GetMapping
    public Result tags(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findTagsDetailById(@PathVariable("id")Long id){
        return tagService.findTagsDetailById(id);
    }
}
