package org.jeecg.modules.qe.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.qe.entity.CoinBot;
import org.jeecg.modules.qe.entity.CoinKeys;
import org.jeecg.modules.qe.entity.CoinSettlementDayReward;
import org.jeecg.modules.qe.entity.CoinTrader;
import org.jeecg.modules.qe.mapper.CoinBotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component("cgformEnhanceBeanStartBot")
public class CustomCoinBotService  implements CgformEnhanceJavaInter {

    @Autowired
    CoinTraderServiceImpl coinTraderService;

    @Autowired
    CoinSettlementDayRewardServiceImpl coinSettlementDayReward;

    @Autowired
    CoinKeysServiceImpl coinKeysService;

    @Autowired
    CoinBotMapper coinBotMapper;
    @Override
    public void execute(String s, JSONObject jsonObject) throws BusinessException {

        CoinBot coinBot = JSON.parseObject(jsonObject.toJSONString(), CoinBot.class);
        coinBot.setStatus("1");
        int i = coinBotMapper.updateById(coinBot);


    }

    public boolean payForMarket(String ids,String createId) {
        String[] split = ids.split(",");

        List<CoinSettlementDayReward> coinSettlementDayRewards = coinSettlementDayReward.listByIds(Arrays.asList(split));
        List<String> memberids = coinSettlementDayRewards.stream().map(k -> k.getMemberId()).collect(Collectors.toList());
        QueryWrapper<CoinKeys>queryWrapper=new QueryWrapper();

        queryWrapper.in("member_id",memberids);
        queryWrapper.eq("env","prod");
        List<CoinKeys> coinKeys = coinKeysService.list(queryWrapper);


        if (coinSettlementDayRewards.isEmpty()){
            return  true;
        }
        Date currentDatae=new Date();
        List<CoinTrader> traders = coinSettlementDayRewards.stream().map(r -> {
            List<CoinKeys> memberKeys = coinKeys.stream().filter(c -> c.getMemberId().equals(r.getMemberId())).collect(Collectors.toList());
            CoinTrader coinTrader = new CoinTrader();
            if (!memberKeys.isEmpty()){
                CoinKeys key = memberKeys.get(0);
                coinTrader.setToAccount(key.getId());

            }

            coinTrader.setCreateBy(createId);
            coinTrader.setCreateTime(currentDatae);
            coinTrader.setExchange(r.getExchange());
            coinTrader.setStatus("pay_no");
            coinTrader.setDescr("市场奖励");
            coinTrader.setPaymount(r.getReward());
            coinTrader.setSymbol("USDT");
            coinTrader.setFromAccount("1895041353668202497");
            coinTrader.setSysOrgCode("A01");
            return coinTrader;

        }).collect(Collectors.toList());

        coinTraderService.saveBatch(traders);

        return  true;
    }
}
