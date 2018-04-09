package com.zhangxi;

import com.zhangxi.service.RedisService;
import com.zhangxi.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    private TestService service;
    @Autowired
    RedisService redisService;

    @Test
    public void contextLoads() {
        Map<String, Object> map = service.getMobile(1);
        System.out.println(map);
        redisService.set("myFirstKey", "zhangxi");
        String firstKey = redisService.get("myFirstKey");
        System.out.println("myFirstKey:" + firstKey);
    }
}
