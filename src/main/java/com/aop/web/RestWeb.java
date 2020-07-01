package com.aop.web;

import com.aop.common.RestURL;
import com.aop.service.RestTemplateService;
import com.aop.vo.UserVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description :
 * @Author : wsz
 * @Date: 2020-06-30 19:33
 */
@RestController
@RequestMapping("/rest")
public class RestWeb {

    @Autowired
    RestTemplateService restTemplateService;

    @GetMapping(value = "/get")
    public String restGet () {
        String forObject = "1111";
        try {
            forObject = restTemplateService.getForObject("http://localhost:8088/aop/str?a=123&b=456", String.class);
            System.out.println(forObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return forObject;
    }

    @GetMapping(value = "/getMap")
    public UserVo getMap () {
        UserVo vo = new UserVo();
        try {
            Map<String,Object> map = new HashMap();
            map.put("name","1111");
            map.put("age",1111);

            vo = restTemplateService.getForObject(
                    restTemplateService.getRestURL() + RestURL.GetMap + "?age={age}&name={name}",
                    UserVo.class,
                    map);
            System.out.println(vo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vo;
    }

    @GetMapping(value = "/postMap")
    public Map postMap () {
        Map vo = Maps.newHashMap();
        try {

            Map<String,Object> map = Maps.newHashMap();
            map.put("name","1111");
            map.put("age",1111);

            map.put("haha", Lists.newArrayList("1", "2"));

            vo = restTemplateService.postForObject(restTemplateService.getRestURL() + RestURL.PostMap,
                    map,
                    Map.class);
            System.out.println(vo);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return vo;
    }
}
