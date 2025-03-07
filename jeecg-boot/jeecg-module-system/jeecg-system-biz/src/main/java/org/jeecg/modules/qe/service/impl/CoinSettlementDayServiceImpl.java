package org.jeecg.modules.qe.service.impl;

import org.jeecg.modules.qe.entity.CoinSettlementDay;
import org.jeecg.modules.qe.entity.CoinSettlementDayDetail;
import org.jeecg.modules.qe.entity.CoinSettlementDayReward;
import org.jeecg.modules.qe.mapper.CoinSettlementDayDetailMapper;
import org.jeecg.modules.qe.mapper.CoinSettlementDayRewardMapper;
import org.jeecg.modules.qe.mapper.CoinSettlementDayMapper;
import org.jeecg.modules.qe.service.ICoinSettlementDayService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 每日结算
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
@Service
public class CoinSettlementDayServiceImpl extends ServiceImpl<CoinSettlementDayMapper, CoinSettlementDay> implements ICoinSettlementDayService {

	@Autowired
	private CoinSettlementDayMapper coinSettlementDayMapper;
	@Autowired
	private CoinSettlementDayDetailMapper coinSettlementDayDetailMapper;
	@Autowired
	private CoinSettlementDayRewardMapper coinSettlementDayRewardMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		coinSettlementDayDetailMapper.deleteByMainId(id);
		coinSettlementDayRewardMapper.deleteByMainId(id);
		coinSettlementDayMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			coinSettlementDayDetailMapper.deleteByMainId(id.toString());
			coinSettlementDayRewardMapper.deleteByMainId(id.toString());
			coinSettlementDayMapper.deleteById(id);
		}
	}
	
}
