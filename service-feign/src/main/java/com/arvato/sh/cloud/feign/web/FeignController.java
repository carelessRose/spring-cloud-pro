package com.arvato.sh.cloud.feign.web;

import com.alibaba.fastjson.JSONObject;
import com.arvato.sh.cloud.feign.entity.SysUser;
import com.arvato.sh.cloud.feign.service.FeignServiceInterface;
import com.arvato.sh.cloud.feign.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FeignController
 * @Auther: ROSE
 * @Date: 2018/7/16 15:46
 * @Description:
 **/
@RestController
public class FeignController {

    private Logger logger = LoggerFactory.getLogger(FeignController.class);

    @Autowired
    private FeignServiceInterface feignServiceInterface;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping(value = "/feignHi")
    public String feignHi(@RequestParam String name){
        return feignServiceInterface.sayHiFromFeign(name);
    }

    @GetMapping(value = "/getAllUser")
    public String getAllUsers(SysUser sysUser){
        return JSONObject.toJSONString(sysUserService.getAllUserList(sysUser));
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(SysUser sysUser){
        Map<String,Object> map = new HashMap<>();
        try{
            sysUserService.saveUser(sysUser);
            map.put("success","S");
            map.put("msg","save success！");
        }catch (SQLException se){
            logger.error(se.getMessage());
            map.put("success","F");
            map.put("msg","database insert failed");
        }catch (Exception e){
            logger.error(e.getMessage());
            map.put("success","F");
            map.put("msg","save failed:"+e.getMessage());
        }
        return JSONObject.toJSONString(map);
    }

    /**
     *  @Auther: ROSE
     *  @Description: 根据查询条件查询用户，分页
     *  @Date: 2018/8/15 9:51
     *  @Param: sysUser pageNum：当前页数 pageSize：每页数量：pageSize
     *  @Return:
     **/
    @GetMapping(value = "/getUsers")
    public String getUsers(SysUser sysUser,@RequestParam(name = "pageNum",required = false)int pageNum,
                           @RequestParam(name = "pageSize",required = false)int pageSize){
        return JSONObject.toJSONString(sysUserService.getUsers(sysUser,pageNum,pageSize));
    }

    @GetMapping(value = "/getRedisHash")
    public String getRedisHash(){
        return sysUserService.getRedisHash();
    }

    @GetMapping(value = "getUserByName")
    public String getUserByName(@RequestParam(name = "name") String name){
        return sysUserService.getUserByNameFromRedis(name);
    }
}
