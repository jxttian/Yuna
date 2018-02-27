package net.myscloud.open.yuna.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.myscloud.open.yuna.consts.CacheName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfiguration {

    /**
     * 默认失效时间
     */
    @Value("${local.cache.expire:60}")
    private Long expire;

    /**
     * 默认最大大小
     */
    @Value("${local.cache.maxsize:1000}")
    private Integer maxsize;

    @Bean
    public KeyGenerator keyBuilder() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            Stream.of(target.getClass().getName(), method.getName(), params).forEach(sb::append);
            return sb.toString();
        };
    }

    @Bean
    public CacheManager localCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<GuavaCache> caches = Lists.newArrayList();
        Stream.of(CacheName.values()).forEach(cacheName -> {
            CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
            if (!cacheName.isForever()) {
                if (cacheName.getExpire() != null) {
                    cacheBuilder.expireAfterWrite(cacheName.getExpire(), TimeUnit.SECONDS);
                } else if (expire > 0) {
                    cacheBuilder.expireAfterWrite(expire, TimeUnit.SECONDS);
                }
            }
            if (!cacheName.isForever()) {
                if (cacheName.getMaxsize() != null) {
                    cacheBuilder.maximumSize(cacheName.getMaxsize());
                } else if (maxsize > 0) {
                    cacheBuilder.maximumSize(maxsize);
                }
            }
            caches.add(new GuavaCache(cacheName.getName(),
                    cacheBuilder.build()));
        });
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}

