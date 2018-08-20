package com.arvato.sh.cloud.ribbon.web;

import com.arvato.sh.cloud.ribbon.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RibbonController
 * @Auther: ROSE
 * @Date: 2018/7/16 15:24
 * @Description:
 **/
@RestController
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;

    @RequestMapping("/ribbonHi")
    public String ribbonHi(@RequestParam String name){
        //return restTemplate.getForObject("http://service-miya/hi", String.class);
        return ribbonService.hiService(name);
    }

    @RequestMapping("/info")
    public String info(){
        return "i'm service-ribbon";

    }
}
