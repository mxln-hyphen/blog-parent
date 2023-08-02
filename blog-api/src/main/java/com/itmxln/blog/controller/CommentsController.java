package com.itmxln.blog.controller;

import com.itmxln.blog.service.CommentsService;
import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;


    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id")Long id){
        return commentsService.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }

}
