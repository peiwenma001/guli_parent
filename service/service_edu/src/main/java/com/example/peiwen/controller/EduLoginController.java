package com.example.peiwen.controller;

import org.springframework.web.bind.annotation.*;
import peiwen.commonutils.R;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){

        return R.ok().data("token","admin");
    }
    //getinfo
    @GetMapping("info")
    public R info(){

        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://img1.baidu.com/it/u=2646421882,3532677684&fm=253&fmt=auto&app=138&f=JPEG?w=281&h=500");
    }
}
