package com.itmxln.blog.controller;

import com.itmxln.blog.service.LoginService;
import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;


    @GetMapping
    public Result logout(@RequestHeader("Authorization")String token){
        return loginService.logout(token);
    }



}