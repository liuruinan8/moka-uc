package com.zimokaka.uc.redis.config;

import com.zimokaka.uc.redis.serialize.RedisObjectSerializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wupl
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    private static Logger logger = Logger.getLogger(RedisConfig.class);
    @Value("${spring.redis.host}")
    private String redisHost;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        factory.setHostName(redisHost);
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }

    @Bean(value = "mokaRedisTemplate")
    public RedisTemplate<?, ?> getRedisTemplate() {
        RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }
}

