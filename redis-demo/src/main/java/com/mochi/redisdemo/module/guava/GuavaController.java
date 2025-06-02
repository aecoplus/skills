package com.mochi.redisdemo.module.guava;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther jiangxl
 * @date 2025/5/14
 */
@RestController
@RequestMapping("/guava")
@Slf4j
public class GuavaController {

    @GetMapping()
    public List<String> list() {
        // 创建一个字符串数组
        String[] rest = {"apple", "banana", "cherry", "date", "elderberry"};
        List<String> list = Lists.asList("1", rest);
        log.info("list: {}", list);
        return list;
    }

}
