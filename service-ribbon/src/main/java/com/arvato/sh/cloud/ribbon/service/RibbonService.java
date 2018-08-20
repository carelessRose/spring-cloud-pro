package com.arvato.sh.cloud.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RibbonService
 * @Auther: ROSE
 * @Date: 2018/7/16 15:21
 * @Description:
 **/
@Service
public class RibbonService {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name){
        return restTemplate.getForObject("http://service-hi/index/hi?name="+name,String.class);
        //return restTemplate.getForObject("http://localhost:8989/hi", String.class);
    }

    public String hiError(String name){
        return "hi " + name +", i am so sorry ";
    }
}
