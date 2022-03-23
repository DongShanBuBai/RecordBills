package com.lil.maven.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * 缓存管理
 * redis配置类
 * 使用jedis连接池连接redis
 * @Author:lil
 * @Date: 2022-03-20
 */
//开启缓存
@EnableCaching
@PropertySource({"classpath:application.properties"})
@Configuration
public class RedisConfig extends CachingConfigurerSupport{
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.pool.maxActive}")
    private int maxActive;
    @Value("${spring.redis.pool.maxWait}")
    private int maxWait;
    @Value("${spring.redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${spring.redis.pool.minIdle}")
    private int minIdle;

    /**
     * 自定义缓存key的生成策略
     * 调用@Cacheable注解的方法时，若传入的参数相同则使用缓存
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator(){
        //函数式接口可以使用lambda表达式创建实例
        return (target,method,params) ->{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(target.getClass().getName());
            stringBuilder.append(method.getName());
            for (Object obj : params){
                stringBuilder.append(obj.toString());
            }
            return stringBuilder.toString();
        };
    }

    /**自定义连接池配置
     * @return
     */
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置最大连接数
        jedisPoolConfig.setMaxTotal(maxActive);
        //设置最大空闲连接数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(minIdle);
        //没有可用连接时，最大等待时间，单位毫秒
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        return jedisPoolConfig;
    }

    /**
     * Spring Data Redis 2.0开始已经不推荐直接显示设置连接
     * 须通过以下繁琐的方法配置
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setPassword(password);

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder= JedisClientConfiguration.builder();
        //设置连接时间
        jedisClientConfigurationBuilder.connectTimeout(Duration.ofSeconds(60));
        //配置连接池
        jedisClientConfigurationBuilder.usePooling().poolConfig(jedisPoolConfig());

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration,jedisClientConfigurationBuilder.build());
        return jedisConnectionFactory;
    }

    /**
     * 自定义RedisTemplate
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 配置缓存管理
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory);
        //设置缓存整体过期时间20分钟
        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().
                entryTtl(Duration.ofMinutes(20));

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter,defaultCacheConfiguration);
        return redisCacheManager;
    }
}

