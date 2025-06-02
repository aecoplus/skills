package com.mochi.dockerdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @auther jiangxl
 * @date 2025/6/2
 */
@RestController
@RequestMapping("/v1/demo")
@Slf4j
public class DockerDemoController {

    @GetMapping("/test-01")
    public String test01() {
        log.info("api request test01");
        return LocalDateTime.now().toString();
    }

}
