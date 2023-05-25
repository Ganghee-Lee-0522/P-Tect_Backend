package com.pyeontect.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ResourceLoader resourceLoader;

    public TestController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/sound")
    public Resource sound() {
        // HTML 파일의 경로를 지정하여 Resource 로드
        Resource resource = resourceLoader.getResource("classpath:static/soundlogic.html");

        // 응답으로 반환
        return resource;
    }

    @PostMapping("/report")
    public Resource report() {
        // HTML 파일의 경로를 지정하여 Resource 로드
        Resource resource = resourceLoader.getResource("classpath:static/reportlogic.html");

        // 응답으로 반환
        return resource;
    }
}
