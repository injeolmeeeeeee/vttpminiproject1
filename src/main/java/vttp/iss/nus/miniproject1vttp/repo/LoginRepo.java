package vttp.iss.nus.miniproject1vttp.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.iss.nus.miniproject1vttp.config.RedisConfig;
import vttp.iss.nus.miniproject1vttp.model.RaceResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class LoginRepo {

    @Autowired
    @Qualifier("formula1Cache")
    private RedisTemplate<String, vttp.iss.nus.miniproject1vttp.model.RaceResult> formula1CacheTemplate;

    @Autowired
    private RedisConfig redisConfig;

    public void cacheSearchResults(String username, List<RaceResult> searchResults) {
        String key = mkKey(username);
        HashOperations<String, String, RaceResult> hashOps = formula1CacheTemplate.opsForHash();

        for (RaceResult result : searchResults) {
            String fieldKey = username;
            hashOps.put(key, fieldKey, result);
        }

        formula1CacheTemplate.expire(key, redisConfig.getTimeout(), TimeUnit.MINUTES);
        System.out.println("caching for " + username);
    }

    @Cacheable(value = "formula1Cache", key = "#username")
    public List<RaceResult> getSearchResults(String username) {
        String key = mkKey(username);
        HashOperations<String, String, RaceResult> hashOps = formula1CacheTemplate.opsForHash();

        if (hashOps.hasKey(key, username)) {
            RaceResult cachedResult = hashOps.get(key, username);
            System.out.println("Data exist. Retrieving cached data for " + username);
            return List.of(cachedResult);
        } else {
            System.out.println("No cached data for " + username);
            return List.of();
        }
    }

    private String mkKey(String username) {
        return "userResults-%s".formatted(username);
    }
}