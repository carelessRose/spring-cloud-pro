package com.arvato.sh.cloud.feign.service;

import com.arvato.sh.cloud.feign.entity.SysUser;
import com.github.pagehelper.PageInfo;

import java.sql.SQLException;
import java.util.List;

public interface SysUserService {

    List<SysUser>getAllUserList(SysUser sysUser);

    void saveUser(SysUser sysUser) throws SQLException;

    PageInfo<SysUser> getUsers(SysUser sysUser,int pageNum,int pageSize);

    String getRedisHash();

    String getUserByNameFromRedis(String name);
}
