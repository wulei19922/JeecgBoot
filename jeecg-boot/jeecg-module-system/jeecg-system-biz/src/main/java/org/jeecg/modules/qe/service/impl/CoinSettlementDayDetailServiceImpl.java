package org.jeecg.modules.qe.service.impl;

import org.jeecg.modules.qe.entity.CoinSettlementDayDetail;
import org.jeecg.modules.qe.mapper.CoinSettlementDayDetailMapper;
import org.jeecg.modules.qe.service.ICoinSettlementDayDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 机器人点数利润明细
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
@Service
public class CoinSettlementDayDetailServiceImpl extends ServiceImpl<CoinSettlementDayDetailMapper, CoinSettlementDayDetail> implements ICoinSettlementDayDetailService {
	
	@Autowired
	private CoinSettlementDayDetailMapper coinSettlementDayDetailMapper;
	
	@Override
	public List<CoinSettlementDayDetail> selectByMainId(String mainId) {
		return coinSettlementDayDetailMapper.selectByMainId(mainId);
	}
}
