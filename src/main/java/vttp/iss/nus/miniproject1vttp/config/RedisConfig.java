package vttp.iss.nus.miniproject1vttp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import vttp.iss.nus.miniproject1vttp.model.RaceResult;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.username}")
    private String redisUsername;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${formula1.cache.timeout.minutes}")
    private long timeout;

    public long getTimeout() {
        return timeout;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RaceResult raceResult() {
        return new RaceResult();
    }

    @Bean("redis")
    public JedisConnectionFactory jedisConnFactory() {

        System.out.println(redisUsername);
        System.out.println(redisPassword);
        System.out.println(redisHost);
        System.out.println(redisPort);

        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(redisHost, redisPort);

        // if (redisUsername != null && !redisUsername.isEmpty()) {
        //     rsc.setUsername(redisUsername);
        // }

        // if (redisPassword != null && !redisPassword.isEmpty()) {
        //     rsc.setPassword(redisPassword);
        // }

        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(rsc, jedisClient);
        jedisFac.afterPropertiesSet();

        return jedisFac;
    }

    @Bean("cache")
    public HashOperations<String, String, RaceResult> hashOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean("formula1Cache")
    public RedisTemplate<String, RaceResult> formula1CacheTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, RaceResult> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}