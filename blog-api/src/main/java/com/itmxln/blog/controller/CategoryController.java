package com.itmxln.blog.controller;

import com.itmxln.blog.service.CategoryService;
import com.itmxln.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categories(){
        return categoryService.findAll();
    }

    @GetMapping("detail")
    public Result detail(){
        return categoryService.findALLDetail();
    }

    @GetMapping("detail/{id}")
    public Result findCategoryDetailById(@PathVariable("id")Long id){
        return categoryService.findCategoryDetailById(id);
    }

}


