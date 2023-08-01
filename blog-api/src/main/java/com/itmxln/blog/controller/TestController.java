package com.itmxln.blog.controller;

import com.itmxln.blog.dao.pojo.SysUser;
import com.itmxln.blog.utils.UserThreadLocal;
import com.itmxln.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        return Result.success(null);
    }
}
