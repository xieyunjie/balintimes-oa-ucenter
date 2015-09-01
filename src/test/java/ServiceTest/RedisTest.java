package ServiceTest;

import model.authority.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.JsonUtil;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by AlexXie on 2015/8/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

public class RedisTest {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void SetEmp() {
        Employee emp = new Employee();
        emp.setUid(UUID.randomUUID().toString());
        emp.setEmployeename("alexxie");
        emp.setUsername("admin");
        emp.setEmail("abc@qq.com");

//        redisTemplate.opsForValue().set("currentuser", JsonUtil.ToJson(emp));
        redisTemplate.delete("currentuser");
        redisTemplate.opsForValue().set("currentuser", JsonUtil.ToJson(emp), 1, TimeUnit.MINUTES);
    }

    @Test
    public void GetEmp() {

//        String s = redisTemplate.opsForValue().get("currentuser");
//        System.out.println(s);
//        Employee emp = JsonUtil.ToObject(s, Employee.class);
//        System.out.println(emp.toString());

        String s = redisTemplate.opsForValue().get("currexxxntuser");
        System.out.println(s);
    }


}
