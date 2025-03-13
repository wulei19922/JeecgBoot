package org.jeecg.modules.demo.m.service;

import org.jeecg.modules.demo.m.entity.MarketVouchersMerchants;
import org.jeecg.modules.demo.m.entity.MarketVouchers;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 支付营销代金券场景
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
public interface IMarketVouchersService extends IService<MarketVouchers> {

	/**
	 * 添加一对多
	 *
	 * @param marketVouchers
	 * @param marketVouchersMerchantsList
	 */
	public void saveMain(MarketVouchers marketVouchers,List<MarketVouchersMerchants> marketVouchersMerchantsList) ;
	
	/**
	 * 修改一对多
	 *
   * @param marketVouchers
   * @param marketVouchersMerchantsList
	 */
	public void updateMain(MarketVouchers marketVouchers,List<MarketVouchersMerchants> marketVouchersMerchantsList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
