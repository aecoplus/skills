package com.mochi.redisdemo.module.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

/**
 * guava实现的简单布隆过滤器
 *
 * @auther jiangxl
 * @date 2025/5/14
 */
@RestController
@RequestMapping("/bloom")
@RequiredArgsConstructor
@Slf4j
public class BloomController {

    private final RBloomFilter<String> bloomFilter;

    /**
     * redis的布隆过滤器-查询
     *
     * @param name
     * @return
     */
    @GetMapping("/{name}")
    public String redis(@PathVariable String name) {
        boolean contains = bloomFilter.contains(name);
        return "redis contains " + name + " : " + contains;
    }

    /**
     * redis的布隆过滤器-保存
     *
     * @param name
     * @return
     */
    @PostMapping("/{name}")
    public String addBloom(@PathVariable String name) {
        bloomFilter.add(name);
        return "added: " + name;
    }

    /**
     * guava实现的简单布隆过滤器
     *
     * @param name
     * @return
     */
    @GetMapping("/guava/{name}")
    @Deprecated
    public String getBloom(@PathVariable String name){
        // 创建一个布隆过滤器，预计插入元素数量为500，误报率为0.03
        BloomFilter<String> bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(Charset.forName("utf-8")), // 使用字符串作为输入类型
                50000,                    // 预计要插入的元素数量
                0.01);                  // 期望的误报率

        // 向布隆过滤器中添加元素
        bloomFilter.put("apple");
        bloomFilter.put("banana");
        bloomFilter.put("cherry");

        // 检查某些元素是否可能存在于布隆过滤器中
        if (bloomFilter.mightContain(name)) {
            System.out.println(name + " 可能存在于集合中.");
        } else {
            System.out.println(name + " 绝对不在集合中.");
        }

        // 检查一个不存在的元素
        name = "orange";
        if (!bloomFilter.mightContain(name)) {
            System.out.println(name + " 绝对不在集合中.");
        }
        return "bloom";
    }

}
