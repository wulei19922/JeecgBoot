package org.jeecg.modules.qe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.qe.entity.CoinBot;
import org.jeecg.modules.qe.mapper.CoinBotMapper;
import org.jeecg.modules.qe.service.ICoinBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 机器人列表
 * @Author: jeecg-boot
 * @Date:   2025-02-18
 * @Version: V1.0
 */
@Service
public class CoinBotServiceImpl extends ServiceImpl<CoinBotMapper, CoinBot> implements ICoinBotService {



    @Autowired
    CoinBotMapper coinBotMapper;
    @Override
    public boolean operateBatch(List<String> ids, String type) {
        List<CoinBot>bots=new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            CoinBot byId = getById(ids.get(i));
            if(type.equals("start")){
                byId.setStatus("1");
            }else{
                byId.setStatus("6");
            }
            bots.add(byId);
        }
        updateBatchById(bots);
        return false;
    }

    @Override
    public boolean check(CoinBot coinBot) {
        LambdaQueryWrapper <CoinBot>queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(CoinBot::getMemberId,coinBot.getMemberId());
        queryWrapper.eq(CoinBot::getSymbol,coinBot.getSymbol());
        queryWrapper.eq(CoinBot::getCategoryType,coinBot.getCategoryType());
        List<CoinBot> bots = coinBotMapper.selectList(queryWrapper);
        if (StringUtils.hasText(coinBot.getId())){
            //编辑时对当前订单有效
            CoinBot coinBot1 = bots.stream().filter(t -> t.getId().equals(coinBot.getId())).findFirst().orElse(null);
            if(coinBot1!=null){
                return  true;
            }
        }else{
            if(bots.isEmpty()){
                return  true;
            }else{
                return false;

            }
        }
        return false;
    }

    @Override
    public boolean editGrideConfig(String id, String gridConfig, Float addInvest) {
       try {
           //初始化结果
           CoinBot coinBot = this.getById(id);
           //更改为重启中
           coinBot.setStatus("8");
           //更新配置
           coinBot.setGridConfig(gridConfig);
           coinBot.setTotalInvest(coinBot.getTotalInvest()+Double.parseDouble(addInvest.toString()));
           this.updateById(coinBot);
           return  true;
       }catch (Exception e ){
           return false;
       }
    }
}
