package org.jeecg.modules.demo.m.mapper;

import java.util.List;
import org.jeecg.modules.demo.m.entity.MarketVouchersMerchants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 代金券场景归属商户
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
public interface MarketVouchersMerchantsMapper extends BaseMapper<MarketVouchersMerchants> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<MarketVouchersMerchants>
   */
	public List<MarketVouchersMerchants> selectByMainId(@Param("mainId") String mainId);
}
