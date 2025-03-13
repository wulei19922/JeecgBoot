package org.jeecg.modules.demo.m.service.impl;

import org.jeecg.modules.demo.m.entity.MarketVouchers;
import org.jeecg.modules.demo.m.entity.MarketVouchersMerchants;
import org.jeecg.modules.demo.m.mapper.MarketVouchersMerchantsMapper;
import org.jeecg.modules.demo.m.mapper.MarketVouchersMapper;
import org.jeecg.modules.demo.m.service.IMarketVouchersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 支付营销代金券场景
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
@Service
public class MarketVouchersServiceImpl extends ServiceImpl<MarketVouchersMapper, MarketVouchers> implements IMarketVouchersService {

	@Autowired
	private MarketVouchersMapper marketVouchersMapper;
	@Autowired
	private MarketVouchersMerchantsMapper marketVouchersMerchantsMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(MarketVouchers marketVouchers, List<MarketVouchersMerchants> marketVouchersMerchantsList) {
		marketVouchersMapper.insert(marketVouchers);
		if(marketVouchersMerchantsList!=null && marketVouchersMerchantsList.size()>0) {
			for(MarketVouchersMerchants entity:marketVouchersMerchantsList) {
				//外键设置
				entity.setHaedId(marketVouchers.getId());
				marketVouchersMerchantsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(MarketVouchers marketVouchers,List<MarketVouchersMerchants> marketVouchersMerchantsList) {
		marketVouchersMapper.updateById(marketVouchers);
		
		//1.先删除子表数据
		marketVouchersMerchantsMapper.deleteByMainId(marketVouchers.getId());
		
		//2.子表数据重新插入
		if(marketVouchersMerchantsList!=null && marketVouchersMerchantsList.size()>0) {
			for(MarketVouchersMerchants entity:marketVouchersMerchantsList) {
				//外键设置
				entity.setHaedId(marketVouchers.getId());
				marketVouchersMerchantsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		marketVouchersMerchantsMapper.deleteByMainId(id);
		marketVouchersMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			marketVouchersMerchantsMapper.deleteByMainId(id.toString());
			marketVouchersMapper.deleteById(id);
		}
	}
	
}
