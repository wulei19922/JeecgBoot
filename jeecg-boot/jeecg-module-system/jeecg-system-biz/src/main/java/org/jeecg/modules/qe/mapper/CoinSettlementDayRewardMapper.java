package org.jeecg.modules.qe.mapper;

import java.util.List;
import org.jeecg.modules.qe.entity.CoinSettlementDayReward;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 结算给市场的利润明细
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
public interface CoinSettlementDayRewardMapper extends BaseMapper<CoinSettlementDayReward> {

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
    * @return List<CoinSettlementDayReward>
    */
	public List<CoinSettlementDayReward> selectByMainId(@Param("mainId") String mainId);

}
