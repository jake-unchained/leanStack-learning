package org.example;

import com.google.common.cache.CacheBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Starting point for the spring boot application
 *
 */

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
public class Application
{
    public static void main( String[] args ) throws Exception

    {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CacheManager cacheManager(){
        GuavaCacheManager cacheManager = new GuavaCacheManager(
                "greetings"
        );
        return cacheManager;

    }
}
