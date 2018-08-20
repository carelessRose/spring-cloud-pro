package com.arvato.sh.cloud.feign.service.impl;

import com.arvato.sh.cloud.feign.service.FeignServiceInterface;
import org.springframework.stereotype.Component;

/**
 * @ClassName FeignServiceInterImpl
 * @Auther: ROSE
 * @Date: 2018/7/16 16:30
 * @Description: FeignServiceInterface映射方法失效时调用的断路器方法
 **/
@Component
public class FeignServiceInterImpl implements FeignServiceInterface {

    @Override
    public String sayHiFromFeign(String name){
        return "sorry  " + name + " from feign hystrix";
    }
}
