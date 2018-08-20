package com.arvato.sh.cloud.eurekaClient.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName IndexController
 * @Auther: ROSE
 * @Date: 2018/7/16 14:56
 * @Description:
 **/
@RestController
@RequestMapping("/index")
public class IndexController {

    @Value("${server.port}")
    String port;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "hiError")
    public String hi(@RequestParam(value = "name",defaultValue = "默认值")String name){
        return "I'm "+ name + ", I am from port :" + port;
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }


}
