package org.jeecg.modules.qe.service;

import org.jeecg.modules.qe.entity.CoinWallet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * @Description: 量化钱包
 * @Author: jeecg-boot
 * @Date:   2025-02-27
 * @Version: V1.0
 */
public interface ICoinWalletService extends IService<CoinWallet> {
    public boolean updateUserWallet(String manager, String memberid, Double usdtBalanceAfter, Double currentBanance, BigDecimal amount);
}
