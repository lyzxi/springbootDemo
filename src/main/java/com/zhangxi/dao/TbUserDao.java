package com.zhangxi.dao;

import com.zhangxi.dao.mapper.TbUserMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface TbUserDao extends TbUserMapper {
    Map<String, Object> getUserMobile(@Param("byname") int id);
}
