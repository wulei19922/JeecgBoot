package org.jeecg.modules.qe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.qe.entity.CoinFundsChange;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 资金明细变化
 * @Author: jeecg-boot
 * @Date:   2025-02-26
 * @Version: V1.0
 */
public interface CoinFundsChangeMapper extends BaseMapper<CoinFundsChange> {



    @Update("update  coin_keys set bp_total=bp_total+${amount},bp_point_free=bp_point_free+${amount}  where member_id='${memberid}'")
    public void  userWalletAdd(@Param("amount") Double amount,@Param("memberid") String memberid);

}
