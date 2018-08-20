package com.arvato.sh.cloud.feign.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arvato.sh.cloud.feign.entity.SysUser;
import com.arvato.sh.cloud.feign.service.SysUserService;
import com.arvato.sh.cloud.feign.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName SysUserServiceImpl
 * @Auther: ROSE
 * @Date: 2018/8/10 14:21
 * @Description:
 **/
@Service(value="sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void saveUser(SysUser sysUser) throws SQLException {
        if(sysUserMapper.insertSelective(sysUser)!=1){
            throw new SQLException("insert failed");
        }
        HashOperations<String,String,String> ho = redisTemplate.opsForHash();
        ho.put("USER",sysUser.getName(),JSONObject.toJSONString(sysUser));
    }

    @Override
    public List<SysUser> getAllUserList(SysUser sysUser) {
        HashOperations<String,String,String> ho = redisTemplate.opsForHash();
        ho.put("QUERY_RECORD", JSONObject.toJSONString(sysUser),String.valueOf(System.currentTimeMillis()));
        return sysUserMapper.getList(sysUser);
    }

    @Override
    public PageInfo<SysUser> getUsers(SysUser sysUser,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser>userList = sysUserMapper.getList(sysUser);
        PageInfo result = new PageInfo(userList);
        return result;
    }

    @Override
    public String getRedisHash() {
        HashOperations<String,String,String> ho = redisTemplate.opsForHash();
        Set<String>keys = ho.keys("USER");
        List<String> list = ho.multiGet("USER",keys);
        /*Cursor<Map.Entry<String,String>>cursor = ho.scan("USER", ScanOptions.NONE);
        List<String>list = new LinkedList<>();
        while(cursor.hasNext()){
            Map.Entry<String, String> entry = cursor.next();
            list.add(entry.getValue());
        }*/

        //list转json时会有转义字符出现，需要替换
        String result = JSONObject.toJSONString(list).replace("\\","");
        return result;
    }

    @Override
    public String getUserByNameFromRedis(String name) {
        HashOperations<String,String,String> ho = redisTemplate.opsForHash();
        String json = ho.get("USER",name);
        if(json!=null && !"".equals(json)){
            return json.replace("\\","");
        }else{
            return null;
        }
    }
}
