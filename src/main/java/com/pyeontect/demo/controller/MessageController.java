package com.pyeontect.demo.controller;

import com.pyeontect.demo.service.CertificationService;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final CertificationService certificationService;

    public MessageController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @GetMapping("/test")
    public void test() throws CoolsmsException {
        certificationService.certifiedPhoneNumber();
    }
}
