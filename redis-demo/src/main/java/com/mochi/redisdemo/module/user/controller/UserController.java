package com.mochi.redisdemo.module.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @auther jiangxl
 * @date 2025/5/13
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @GetMapping()
    public String list() {
        log.info("users list");
        return "users list";
    }

    @GetMapping("/{id}")
    public String item(@PathVariable String id) {
        log.info("get user item");
        return "users item: " + id;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody Map<String, String> params) {
        log.info("update user item");
        return "users update: " + id + ": " + params;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        log.info("delete user item");
        return "users delete: " + id;
    }
}
