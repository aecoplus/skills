package com.mochi.redisdemo.configuration;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther jiangxl
 * @date 2025/5/14
 */
@Configuration
@RequiredArgsConstructor
public class BloomFilterConfig {

    private final RedissonClient redissonClient;

    @Bean
    public RBloomFilter<String> userBloomFilter() {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("userFilter");
        // 预期元素量100万，误判率3%
        bloomFilter.tryInit(1000000L, 0.03);
        return bloomFilter;
    }
}

