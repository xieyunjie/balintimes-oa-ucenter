package util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by AlexXie on 2015/8/24.
 */
@Component
public class RedisUserUtil {

    private final String WEBUSER = "ucenter-WebUser-";
    private final String UserPermission = "ucenter-USERPERMISSION-";
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public String SetRedisWebUser(String data) {
        String uid = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(WEBUSER + uid, data);

        return uid;

    }

    public String GetRedisWebUser(String uid) {

        String user = redisTemplate.opsForValue().get(WEBUSER + uid);

        return user;
    }

}

