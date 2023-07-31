package com.itmxln.blog.service;

import com.itmxln.blog.dao.pojo.SysUser;
import com.itmxln.blog.vo.Result;
import com.itmxln.blog.vo.params.LoginParam;

public interface LoginService {
    /**
     * 登陆功能
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);
}
