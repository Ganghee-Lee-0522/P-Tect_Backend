package com.pyeontect.demo.controller;

import com.pyeontect.demo.DemoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class SseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final DemoRepository demoRepository;

    public SseController(JdbcTemplate jdbcTemplate, DemoRepository demoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.demoRepository = demoRepository;
    }

    @GetMapping(value = "/sse/sound", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter handleSoundRequest() {
        SseEmitter emitter = new SseEmitter();

        log.info("로그");
        if (demoRepository.count() != 0) {
            ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
            sseMvcExecutor.execute(() -> {
                try {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data(System.currentTimeMillis());
                    emitter.send(event);
                } catch (Exception ex) {
                    emitter.completeWithError(ex);
                }
            });
        } else {
            emitter.complete(); // SSE 완료
        }

        return emitter;
    }







/*
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data(System.currentTimeMillis());
                    emitter.send(event);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;

 */


    private String readHtmlFile(String filePath) throws IOException {
        // HTML 파일을 읽어서 문자열로 반환
        Path path = Paths.get(filePath);
        byte[] htmlBytes = Files.readAllBytes(path);
        return new String(htmlBytes, StandardCharsets.UTF_8);
    }
}