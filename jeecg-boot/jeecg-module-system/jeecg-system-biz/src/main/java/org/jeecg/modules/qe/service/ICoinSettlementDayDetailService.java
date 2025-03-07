package org.jeecg.modules.qe.service;

import org.jeecg.modules.qe.entity.CoinSettlementDayDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 机器人点数利润明细
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
public interface ICoinSettlementDayDetailService extends IService<CoinSettlementDayDetail> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<CoinSettlementDayDetail>
   */
	public List<CoinSettlementDayDetail> selectByMainId(String mainId);
}
