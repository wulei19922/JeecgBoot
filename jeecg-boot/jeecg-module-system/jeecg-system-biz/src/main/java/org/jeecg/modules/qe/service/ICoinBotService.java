package org.jeecg.modules.qe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.qe.entity.CoinBot;

import java.util.List;

/**
 * @Description: 机器人列表
 * @Author: jeecg-boot
 * @Date:   2025-02-18
 * @Version: V1.0
 */
public interface ICoinBotService extends IService<CoinBot> {



   public  boolean  operateBatch(List<String> ids, String type);

   public  boolean  check(CoinBot coinBot);

    boolean editGrideConfig(String id, String gridConfig, Float addInvest);
}
