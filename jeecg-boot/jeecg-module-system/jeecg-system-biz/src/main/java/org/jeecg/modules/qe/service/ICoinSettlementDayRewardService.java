package org.jeecg.modules.qe.service;

import org.jeecg.modules.qe.entity.CoinSettlementDayReward;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 结算给市场的利润明细
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
public interface ICoinSettlementDayRewardService extends IService<CoinSettlementDayReward> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<CoinSettlementDayReward>
   */
	public List<CoinSettlementDayReward> selectByMainId(String mainId);
}
