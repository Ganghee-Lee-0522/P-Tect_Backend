package com.pyeontect.demo.controller;

import com.pyeontect.demo.service.CertificationService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final ResourceLoader resourceLoader;
    private final CertificationService certificationService;


    @GetMapping("/sound")
    public Resource sound() {

        // HTML 파일의 경로를 지정하여 Resource 로드
        Resource resource = resourceLoader.getResource("classpath:static/soundlogic.html");

        // 응답으로 반환
        return resource;
    }

    @GetMapping("/report")
    public Resource report() throws CoolsmsException {

        certificationService.certifiedPhoneNumber();

        // HTML 파일의 경로를 지정하여 Resource 로드
        Resource resource = resourceLoader.getResource("classpath:static/reportlogic.html");

        // 응답으로 반환
        return resource;
    }
}
