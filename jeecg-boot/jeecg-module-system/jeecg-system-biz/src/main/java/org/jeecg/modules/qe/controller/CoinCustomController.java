package org.jeecg.modules.qe.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.qe.entity.CoinBot;
import org.jeecg.modules.qe.entity.TickerResutl;
import org.jeecg.modules.qe.service.ICoinBotService;
import org.jeecg.modules.qe.service.impl.BinanceClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: 机器人列表
* @Author: jeecg-boot
* @Date:   2025-02-18
* @Version: V1.0
*/
@Api(tags="自定义接口")
@RestController
@RequestMapping("/qe/custom")
@Slf4j
public class CoinCustomController  {
   @Autowired
   private BinanceClientService binanceClientService;

   @Autowired
   private  ICoinBotService iCoinBotService;

   @ApiOperation(value="查询行情", notes="查询行情-分页列表查询")
   @GetMapping(value = "/tickers/list")
   public Result<List<TickerResutl>> getData() {

       List<TickerResutl> list = binanceClientService.getList(false);

       return Result.OK(list);
   }

    @ApiOperation(value="查询行情", notes="查询行情-分页列表查询")
    @GetMapping(value = "/top/list")
    public Result<List<TickerResutl>> getTopData() {

        List<TickerResutl> list = binanceClientService.getList(true);

        return Result.OK(list);
    }


    @ApiOperation(value="查询用户机器人", notes="查询用户机器人")
    @GetMapping(value = "/bot/list")
    public Result<List<CoinBot>> getBotByMemberInfo() {



        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        QueryWrapper<CoinBot>queryWrapper=new QueryWrapper();
        queryWrapper.eq("member_id",sysUser.getId());
        List<CoinBot> res = iCoinBotService.list(queryWrapper);

        return Result.OK(res);
    }


}
