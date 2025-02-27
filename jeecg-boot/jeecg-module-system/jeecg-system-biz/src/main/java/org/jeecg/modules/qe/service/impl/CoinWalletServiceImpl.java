package org.jeecg.modules.qe.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.qe.FundsChangeEnum;
import org.jeecg.modules.qe.entity.CoinFundsChange;
import org.jeecg.modules.qe.entity.CoinWallet;
import org.jeecg.modules.qe.mapper.CoinFundsChangeMapper;
import org.jeecg.modules.qe.mapper.CoinWalletMapper;
import org.jeecg.modules.qe.service.ICoinWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 量化钱包
 * @Author: jeecg-boot
 * @Date: 2025-02-27
 * @Version: V1.0
 */
@Service
@Slf4j
public class CoinWalletServiceImpl extends ServiceImpl<CoinWalletMapper, CoinWallet> implements ICoinWalletService {
    @Autowired
    CoinFundsChangeMapper coinFundsChangeMapper;

    @Autowired
    CoinFundsChangeServiceImpl coinFundsChangeService;

    public boolean updateUserWallet(String manager, String memberid, Double usdtBalanceAfter, Double currentBanance, BigDecimal amount) {
        CoinFundsChange point = new CoinFundsChange();
        point.setCreateTime(new Date());
        point.setUpdateTime(new Date());
        point.setCreateBy(manager);
        point.setUpdateBy(manager);
        point.setNum(amount.doubleValue());
        point.setMemberId(memberid);//当前用户的变化
        point.setMark("机器人点数充值");
        point.setChangeType(FundsChangeEnum.INCREASE.name());
        point.setFundsType("point");
        point.setNumAfter(usdtBalanceAfter);
        point.setNumBefore(currentBanance);
        coinFundsChangeService.save(point);


        CoinFundsChange usdt = new CoinFundsChange();
        usdt.setCreateTime(new Date());
        usdt.setUpdateTime(new Date());
        usdt.setCreateBy(manager);
        usdt.setUpdateBy(manager);
        usdt.setNum(amount.doubleValue());
        usdt.setMemberId(memberid);//当前用户的变化
        usdt.setMark("机器人点数充值,扣减USDT");
        usdt.setChangeType(FundsChangeEnum.REDUCE.name());
        usdt.setFundsType("usdt");
        usdt.setNumAfter(usdtBalanceAfter);
        usdt.setNumBefore(currentBanance);
        coinFundsChangeService.save(usdt);

        log.info("保存用户钱包变化完成");


        return false;
    }

}
