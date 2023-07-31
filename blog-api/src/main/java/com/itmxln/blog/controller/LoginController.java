package com.itmxln.blog.controller;

import com.itmxln.blog.service.LoginService;
import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;


    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登陆 验证用户
        return loginService.login(loginParam);
    }



}
