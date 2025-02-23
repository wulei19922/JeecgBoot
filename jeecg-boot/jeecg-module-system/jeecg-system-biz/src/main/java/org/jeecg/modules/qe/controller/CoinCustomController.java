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
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.qe.entity.*;
import org.jeecg.modules.qe.service.ICoinBotService;
import org.jeecg.modules.qe.service.ICoinKeysService;
import org.jeecg.modules.qe.service.ICoinVersionService;
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
 * @Date: 2025-02-18
 * @Version: V1.0
 */
@Api(tags = "自定义接口")
@RestController
@RequestMapping("/qe/custom")
@Slf4j
public class CoinCustomController {
    @Autowired
    private BinanceClientService binanceClientService;

    @Autowired
    private ICoinBotService iCoinBotService;

    @Autowired
    private ICoinKeysService iCoinKeysService;

    @Autowired
    private ICoinVersionService iCoinVersionService;

    @ApiOperation(value = "查询行情", notes = "查询行情-分页列表查询")
    @GetMapping(value = "/tickers/list")
    public Result<List<TickerResutl>> getData() {

        List<TickerResutl> list = binanceClientService.getList(false);

        return Result.OK(list);
    }

    @ApiOperation(value = "查询行情", notes = "查询行情-分页列表查询")
    @GetMapping(value = "/top/list")
    public Result<List<TickerResutl>> getTopData() {

        List<TickerResutl> list = binanceClientService.getList(true);

        return Result.OK(list);
    }


    @ApiOperation(value = "查询用户机器人", notes = "查询用户机器人")
    @GetMapping(value = "/bot/list")
    public Result<List<CoinBot>> getBotByMemberInfo() {


        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        QueryWrapper<CoinBot> queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id", sysUser.getId());
        List<CoinBot> res = iCoinBotService.list(queryWrapper);

        return Result.OK(res);
    }

    @ApiOperation(value = "查询用户机器人", notes = "查询用户机器人")
    @GetMapping(value = "/bot/address")
    public Result<ChargeAddress> getAddress() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        ChargeAddress address = binanceClientService.getChargeAddress(sysUser.getId());


        return Result.OK(address);
    }

    @ApiOperation(value = "查询用户机器人", notes = "查询用户机器人")
    @IgnoreAuth
    @GetMapping(value = "/bot/version")
    public Result<CoinVersion> getVersion() {


        QueryWrapper<CoinVersion>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("push","Y");
        queryWrapper.orderBy(true,false,"version_code");
        List<CoinVersion> list = iCoinVersionService.list(queryWrapper);
        if(!list.isEmpty()){
            return Result.OK(list.get(0));
        }else{
            return Result.OK(null);
        }

    }


}
