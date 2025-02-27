package org.jeecg.modules.qe.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.qe.entity.CoinUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 推荐关系
 * @Author: jeecg-boot
 * @Date:   2025-02-26
 * @Version: V1.0
 */
public interface ICoinUserService extends IService<CoinUser> {




    List<Map> getInviteList( String id);
}
