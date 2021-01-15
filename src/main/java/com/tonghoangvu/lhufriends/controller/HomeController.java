package com.tonghoangvu.lhufriends.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Api(tags = "HomeController")
public class HomeController {
    @GetMapping("/")
    @ApiOperation("Index page")
    public String index() {
        return "index";
    }
}
