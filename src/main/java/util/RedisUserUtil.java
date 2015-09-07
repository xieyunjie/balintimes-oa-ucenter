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

    private final static String WebUserID = "ucenter-WebUser-";
    private final static String PerssiomsID = "ucenter-USERPERMISSION-";
    private final static String RolesID = "ucenter-Roles-";
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public String SetRedisWebUser(String userData,String permissionData) {
        String uid = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(WebUserID + uid, userData);
        redisTemplate.opsForValue().set(PerssiomsID + uid, permissionData);

        return uid;
    }

    public String GetRedisWebUser(String uid) {

        String user = redisTemplate.opsForValue().get(WebUserID + uid);

        return user;
    }

}


