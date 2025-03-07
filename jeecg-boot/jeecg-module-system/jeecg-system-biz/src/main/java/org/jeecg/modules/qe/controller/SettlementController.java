package org.jeecg.modules.qe.controller;

import io.lettuce.core.ScriptOutputType;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.qe.service.ICoinSettlementDayRewardService;
import org.jeecg.modules.qe.service.impl.BinanceClientService;
import org.jeecg.modules.qe.service.impl.CustomCoinBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
@RequestMapping("/qe/manager/settlement")
@RestController
public class SettlementController {

    @Autowired
    private ICoinSettlementDayRewardService coinSettlementDayRewardService;

    @Autowired
    private CustomCoinBotService coinBotService;

    @AutoLog(value = "系统结算明细-系统结算明细")
    @ApiOperation(value="系统结算明细-批量删除", notes="系统结算明细-批量结算")
    @RequestMapping(value = "/pay",method = {RequestMethod.POST,RequestMethod.PUT})
    public Result<String> deleteBatchCoinSettlementDayReward(@RequestParam(name="ids",required=true) String ids) {
         LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
       boolean result= coinBotService.payForMarket(ids,loginUser.getId());
//        this.coinSettlementDayRewardService.removeByIds(Arrays.asList(ids.split(",")));
       if(result){
           return Result.OK("生成交易明细成功");
       }else{
           return Result.error("生成交易明细失败");
       }
    }
}
