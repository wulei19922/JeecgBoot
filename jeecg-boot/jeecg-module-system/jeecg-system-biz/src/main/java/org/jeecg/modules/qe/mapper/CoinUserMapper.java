package org.jeecg.modules.qe.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.qe.entity.CoinUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 推荐关系
 * @Author: jeecg-boot
 * @Date:   2025-02-26
 * @Version: V1.0
 */
public interface CoinUserMapper extends BaseMapper<CoinUser> {



    @Select("select g.invitername,count(1)as num from (\n" +
            "select i.invitername,  IF(d.member_id  IS NULL, 0, 1) as num  from (\n" +
            "select b.username as invitername,b.id   from coin_user a left join sys_user b on a.member_id=b.id\n" +
            "                  where a.invited='${id}') i  left join  coin_user d on i.id=d.invited)g group by  g.invitername")
    List<Map> selectInviteInfo(@Param("id")String id);
}
