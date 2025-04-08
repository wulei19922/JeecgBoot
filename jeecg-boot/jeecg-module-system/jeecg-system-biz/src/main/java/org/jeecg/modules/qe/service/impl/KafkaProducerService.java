package org.jeecg.modules.qe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.modules.qe.entity.CoinBot;
import org.jeecg.modules.qe.enums.CoinBotEnum;
import org.jeecg.modules.qe.service.ICoinBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "example_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ICoinBotService coinBotService;

    public void sendMessage(String message, CoinBotEnum statusChange, String botIds) {
        LambdaQueryWrapper<CoinBot>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CoinBot::getId,botIds.split(","));
        List<CoinBot> list = coinBotService.list();
        list.forEach(item->{
            kafkaTemplate.send(item.getInstanceName(), item.getId(), item.getId()+"-"+message);
        });
    }
}