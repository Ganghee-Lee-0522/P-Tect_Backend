package com.pyeontect.ec2test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RestController
public class HelloController {
    @RequestMapping("/hello")
    //@GetMapping("/hello")
    public String hello() {
        return "soundtest";
    }

    @RequestMapping("/hello/test")
    //@GetMapping("/hello/test")
    public String test() {
        return "report";
    }
}
