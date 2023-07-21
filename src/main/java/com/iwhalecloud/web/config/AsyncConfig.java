package com.iwhalecloud.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration // 表明该类是一个配置类
@EnableAsync // 开启异步事件的支持
public class AsyncConfig {

    @Value("${myProps.corePoolSize}")
    private int corePoolSize;

    @Value("${myProps.maxPoolSize}")
    private int maxPoolSize;

    @Value("${myProps.queueCapacity}")
    private int queueCapacity;

    @Value("${myProps.threadNamePrefix}")
    private String threadNamePrefix;

    @Value("${myProps.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        //线程前缀名
        executor.setThreadNamePrefix(threadNamePrefix);
        //线程存活时间
        executor.setKeepAliveSeconds(keepAliveSeconds);

        /**
         * 拒绝处理策略
         * CallerRunsPolicy()：交由调用方线程运行，比如 main 线程。
         * AbortPolicy()：直接抛出异常。
         * DiscardPolicy()：直接丢弃。
         * DiscardOldestPolicy()：丢弃队列中最老的任务。
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
