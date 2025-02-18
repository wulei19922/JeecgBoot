package org.jeecg.modules.qe.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.qe.entity.CoinBot;
import org.jeecg.modules.qe.mapper.CoinBotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("cgformEnhanceBeanStartBot")
public class CustomCoinBotService  implements CgformEnhanceJavaInter {

    @Autowired
    CoinBotMapper coinBotMapper;
    @Override
    public void execute(String s, JSONObject jsonObject) throws BusinessException {

        CoinBot coinBot = JSON.parseObject(jsonObject.toJSONString(), CoinBot.class);
        coinBot.setStatus("1");
        int i = coinBotMapper.updateById(coinBot);


    }
}
