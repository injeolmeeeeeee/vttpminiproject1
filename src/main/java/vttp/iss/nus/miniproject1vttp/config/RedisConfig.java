package vttp.iss.nus.miniproject1vttp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp.iss.nus.miniproject1vttp.model.RaceResult;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private Integer redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean("lastracecache")
    public RedisTemplate<String, List<RaceResult>> redisTemplate() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setUsername(redisUsername);
        config.setPassword(redisPassword);

        JedisConnectionFactory factory = new JedisConnectionFactory(config);
        factory.afterPropertiesSet();

        RedisTemplate<String, List<RaceResult>> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jdkSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jdkSerializer);

        return template;
    }
}
