package org.jeecg.modules.qe.service;

import org.jeecg.modules.qe.entity.CoinSettlementDayDetail;
import org.jeecg.modules.qe.entity.CoinSettlementDayReward;
import org.jeecg.modules.qe.entity.CoinSettlementDay;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 每日结算
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
public interface ICoinSettlementDayService extends IService<CoinSettlementDay> {

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
