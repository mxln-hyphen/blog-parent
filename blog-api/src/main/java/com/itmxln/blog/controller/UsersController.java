package com.itmxln.blog.controller;

import com.itmxln.blog.service.SysUserService;
import com.itmxln.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization")String token){
        return sysUserService.findUserByToken(token);
    }

}
