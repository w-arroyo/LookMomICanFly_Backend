package com.alvarohdezarroyo.lookmomicanfly.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Service
public class RedisTokenService {

    private final Duration tokenLength;
    private final String tokenPrefix;
    private final String userTokenPrefix;

    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    // needs VALUE injection because AppConfig is initialized after this service and i don't think Lazy Loading is the best option

    public RedisTokenService(StringRedisTemplate stringRedisTemplate, @Value("${app.redisTokenPrefix}") String tokenPrefix, @Value("${app.tokenLength}") String tokenLength, @Value("${app.redisUserIdSetPrefix}") String userTokenPrefix) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.tokenLength = Duration.ofMillis(Integer.parseInt(tokenLength)); // Long.valueOf(AppConfig.getTokenDuration())
        this.tokenPrefix = tokenPrefix;
        this.userTokenPrefix = userTokenPrefix;
    }

    @Transactional
    public void save(String token, String userId, String ip, String device) {
        stringRedisTemplate.opsForHash()
                .putAll(tokenPrefix + token,
                        Map.of("userId", userId,
                                "createdAt", Instant.now().toString(),
                                "ip", "ipppppppppppppppppp",
                                "device", "deviceeeeeeeeeeeeeeeeeeeeeee",
                                "expiresAt", Instant.now().plusSeconds(tokenLength.toSeconds()).toString()));
        stringRedisTemplate.expire(tokenPrefix + token, tokenLength);
        stringRedisTemplate.opsForSet().add(userTokenPrefix + userId, tokenPrefix + token);
    }

    @Transactional
    public boolean removeToken(String userId, String token) {
        final Boolean wasRemoved = stringRedisTemplate.delete(tokenPrefix + token);

        System.out.println("Removing token");

        if (wasRemoved.equals(Boolean.TRUE)) {
            final Long deletedEntriesFromSet = stringRedisTemplate.opsForSet().remove(userTokenPrefix + userId, tokenPrefix + token);
            return deletedEntriesFromSet != null && deletedEntriesFromSet > 0;
        }
        return false;
    }

    public boolean checkIfTokenIsValid(String token) {
        Boolean exists = stringRedisTemplate.hasKey(tokenPrefix + token); // needs to be primitive in order to check if it's null
        if (exists.equals(Boolean.FALSE)) {
            return false;
        }
        final String expiresAt = (String) stringRedisTemplate.opsForHash().get(tokenPrefix + token, "expiresAt");
        if (expiresAt == null) {
            return false;
        }
        return Instant.parse(expiresAt).isAfter(Instant.now());
    }

    public Set<String> getAllTokensByUserId(String userId) {
        return stringRedisTemplate.opsForSet().members(userTokenPrefix + userId);
    }

}
