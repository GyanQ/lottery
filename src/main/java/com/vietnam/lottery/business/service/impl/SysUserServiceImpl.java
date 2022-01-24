package com.vietnam.lottery.business.service.impl;

import com.vietnam.lottery.business.service.SysUserService;
import com.vietnam.lottery.common.config.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Override
    public Map<String, Object> login() {
        //创建token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", 1);
        String token = JwtUtil.createToken(map);
        map.put("token", token);
        return map;
    }
}
