package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")// 这个玩意配置在application.yml里，就是下面jwt令牌对应的三个属性
/**
 * @Data 是 Lombok 提供的注解，它会自动生成：
 * 所有字段的 getter/setter
 * toString()
 * equals() 和 hashCode()
 * 一个全参构造（不是所有情况）
 * 一个无参构造
 */
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
