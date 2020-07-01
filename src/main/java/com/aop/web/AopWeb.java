package com.aop.web;

import com.aop.handler.ReqLog;
import com.aop.vo.UserVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Author : wsz
 * @Date: 2020-05-25 19:41
 */
@RestController
@RequestMapping("/aop")
public class AopWeb {

    @ReqLog(value = "接口测试", desc = "GET")
    @GetMapping(value = "/str")
    public String str (String a, String b) {
        return a + b;
    }

    @GetMapping(value = "/obj")
    public UserVo objGet(UserVo vo) {
        System.out.println(1 / 0);
        return vo;
    }

    /**
     * GET
     * http://localhost:8080/aop/obj/dasd/121?age=123&name=11111111
     * @param vo
     * @param name
     * @param age
     * @return
     */
    @GetMapping(value = "/obj/{name}/{age}")
    public UserVo objGet(UserVo vo, @PathVariable String name, @PathVariable int age) {
        System.out.println(vo);
        vo.setName(name);
        vo.setAge(age);
        return vo;
    }

    /**
     * POST
     * http://localhost:8080/aop/obj?age=123&name=11111111
     * @param vo
     * @return
     */
    @PostMapping(value = "/obj")
    public UserVo objPost(UserVo vo) {
        return vo;
    }

    /**
     * GET
     * http://localhost:8080/aop/map?aa=aaaa&bb=bbbb
     * @param params
     * @return
     */
    @GetMapping(value = "/map")
    public Map mapGet (@RequestParam Map params) {
        return params;
    }

    /**
     * POST
     * http://localhost:8080/aop/map
     * BODY {
     *   "a": "aaaa",
     *   "b": "bbbb"
     * }
     * @param params
     * @return
     */
    @PostMapping(value = "/map")
    public Map mapPost (@RequestBody Map params) {
        return params;
    }

    /**
     * GET
     * http://localhost:8080/aop/list?params=11&params=22&params=33
     * @param params
     * @return
     */
    @GetMapping(value = "/list")
    public List listGet(@RequestParam List params) {
        return params;
    }


    /**
     * POST
     * http://localhost:8080/aop/list?params=11&params=22&params=33
     * @param params
     * @return
     */
    @PostMapping(value = "/list")
    public List listPost(@RequestParam List params, String name) {
        System.out.println(name);
        return params;
    }

    /**
     * POST
     * http://localhost:8080/aop/listObj
     * BODY [
     *   {
     *     "name":"aaa",
     *     "age": 123
     *   }, {
     *     "name":"bbb",
     *     "age": 1111
     *   }
     * ]
     * @param params
     * @return
     */
    @PostMapping(value = "/listObj")
    public List listObj(@RequestBody List<UserVo> params) {
        return params;
    }
}
