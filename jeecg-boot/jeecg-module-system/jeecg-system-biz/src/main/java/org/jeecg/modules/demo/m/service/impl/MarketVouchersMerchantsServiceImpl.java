package org.jeecg.modules.demo.m.service.impl;

import org.jeecg.modules.demo.m.entity.MarketVouchersMerchants;
import org.jeecg.modules.demo.m.mapper.MarketVouchersMerchantsMapper;
import org.jeecg.modules.demo.m.service.IMarketVouchersMerchantsService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 代金券场景归属商户
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
@Service
public class MarketVouchersMerchantsServiceImpl extends ServiceImpl<MarketVouchersMerchantsMapper, MarketVouchersMerchants> implements IMarketVouchersMerchantsService {
	
	@Autowired
	private MarketVouchersMerchantsMapper marketVouchersMerchantsMapper;
	
	@Override
	public List<MarketVouchersMerchants> selectByMainId(String mainId) {
		return marketVouchersMerchantsMapper.selectByMainId(mainId);
	}
}
