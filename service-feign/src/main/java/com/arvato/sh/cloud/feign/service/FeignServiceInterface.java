package com.arvato.sh.cloud.feign.service;

import com.arvato.sh.cloud.feign.service.impl.FeignServiceInterImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-hi",fallback = FeignServiceInterImpl.class)
public interface FeignServiceInterface {

    @RequestMapping(value = "/index/hi",method = RequestMethod.GET)
    String sayHiFromFeign(@RequestParam(value = "name") String name);
}
