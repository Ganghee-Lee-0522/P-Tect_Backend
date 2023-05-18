package com.pyeontect.ec2test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "테스트가 정상적으로 출력됩니다.";
    }
}
