package org.jeecg.modules.demo.m.service;

import org.jeecg.modules.demo.m.entity.MarketVouchersMerchants;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 代金券场景归属商户
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
public interface IMarketVouchersMerchantsService extends IService<MarketVouchersMerchants> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<MarketVouchersMerchants>
	 */
	public List<MarketVouchersMerchants> selectByMainId(String mainId);
}
