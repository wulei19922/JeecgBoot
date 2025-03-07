package org.jeecg.modules.qe.service.impl;

import org.jeecg.modules.qe.entity.CoinSettlementDayReward;
import org.jeecg.modules.qe.mapper.CoinSettlementDayRewardMapper;
import org.jeecg.modules.qe.service.ICoinSettlementDayRewardService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 结算给市场的利润明细
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
@Service
public class CoinSettlementDayRewardServiceImpl extends ServiceImpl<CoinSettlementDayRewardMapper, CoinSettlementDayReward> implements ICoinSettlementDayRewardService {
	
	@Autowired
	private CoinSettlementDayRewardMapper coinSettlementDayRewardMapper;
	
	@Override
	public List<CoinSettlementDayReward> selectByMainId(String mainId) {
		return coinSettlementDayRewardMapper.selectByMainId(mainId);
	}
}
