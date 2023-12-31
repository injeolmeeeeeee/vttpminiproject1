package vttp.iss.nus.miniproject1vttp.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttp.iss.nus.miniproject1vttp.model.RaceResult;
import vttp.iss.nus.miniproject1vttp.repo.LoginRepo;

import java.util.List;

@Service
public class LoginService {

    // @Autowired
    // private RedisTemplate<String, RaceResult> redisTemplate;

    private final LoginRepo loginRepo;
    // @Autowired
    // private RaceResultsService raceResultsService;

    @Autowired
    public LoginService(LoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }

    public List<RaceResult> getLastSearchResults(String username) {
        return loginRepo.getSearchResults(username);
    }
}