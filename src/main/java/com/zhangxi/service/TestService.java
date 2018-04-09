package com.zhangxi.service;

import com.zhangxi.dao.TbUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestService {
    @Autowired
    private TbUserDao userDao;

    public Map<String, Object> getMobile(int id) {
        Map<String, Object> userMap = userDao.getUserMobile(id);
        return userMap;
    }
}
