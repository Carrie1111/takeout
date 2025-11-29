package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，创建并配置 AliOssUtil 实例，注入到Spring容器中
 */
@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    // 单例模式：确保一个 Bean 定义只创建一个实例
    // @ConditionalOnMissingBean：防止同一个类被重复声明为 Bean
    @ConditionalOnMissingBean // 注意这个不是单例的意思，是防止bean的重复声明。
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云上传文工具类对象");
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
