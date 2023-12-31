package vttp.iss.nus.miniproject1vttp.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.iss.nus.miniproject1vttp.model.RaceResult;

import java.util.List;

@Repository
public class LastResultsRepo {

    @Autowired
    private RedisTemplate<String, List<RaceResult>> template;

    public void cacheLastResult(List<RaceResult> lastResult) {
        String cacheKey = "lastrace";
        template.opsForValue().set(cacheKey, lastResult);
    }

    public List<RaceResult> getLastResult() {
        String cacheKey = "lastrace";
        return template.opsForValue().get(cacheKey);
    }
}