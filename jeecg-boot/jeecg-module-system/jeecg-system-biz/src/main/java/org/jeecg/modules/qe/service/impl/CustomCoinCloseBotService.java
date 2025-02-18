package org.jeecg.modules.qe.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.qe.entity.CoinBot;
import org.jeecg.modules.qe.mapper.CoinBotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("cgformEnhanceBeanCloseBot")
public class CustomCoinCloseBotService implements CgformEnhanceJavaInter {

    @Autowired
    CoinBotMapper coinBotMapper;

    @Override
    public void execute(String s, JSONObject jsonObject) throws BusinessException {


        System.out.println("关闭量化机器人");
        System.out.println(s);
        System.out.println(jsonObject);
        CoinBot coinBot = JSON.parseObject(jsonObject.toJSONString(), CoinBot.class);
        coinBot.setStatus("0");
        int i = coinBotMapper.updateById(coinBot);


    }
}
