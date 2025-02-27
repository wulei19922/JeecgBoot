package org.jeecg.modules.qe.service.impl;

import org.checkerframework.checker.units.qual.A;
import org.jeecg.modules.qe.entity.CoinUser;
import org.jeecg.modules.qe.mapper.CoinUserMapper;
import org.jeecg.modules.qe.service.ICoinUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 推荐关系
 * @Author: jeecg-boot
 * @Date:   2025-02-26
 * @Version: V1.0
 */
@Service
public class CoinUserServiceImpl extends ServiceImpl<CoinUserMapper, CoinUser> implements ICoinUserService {


    @Autowired
    CoinUserMapper coinUserMapper;
    @Override
    public List<Map> getInviteList(String id) {

        List<Map> res=coinUserMapper.selectInviteInfo(id);


        return res;
    }
}
