package com.itmxln.blog.service;

import com.itmxln.blog.dao.pojo.SysUser;
import com.itmxln.blog.vo.Result;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account,String password);

    Result findUserByToken(String token);


}
