package com.itmxln.blog.service;

import com.itmxln.blog.dao.pojo.SysUser;
import com.itmxln.blog.vo.Result;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account,String password);

    /**
     * 根据Token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查询用户信息
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存新用户信息
     * @param sysUser
     */
    void save(SysUser sysUser);

}
