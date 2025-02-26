package org.jeecg.modules.qe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.qe.entity.*;
import org.jeecg.modules.qe.service.ICoinBotService;
import org.jeecg.modules.qe.service.ICoinKeysService;
import org.jeecg.modules.qe.service.ICoinUserService;
import org.jeecg.modules.qe.service.ICoinVersionService;
import org.jeecg.modules.qe.service.impl.BinanceClientService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    private ISysUserService sysUserService;

    @Autowired
    private BaseCommonService baseCommonService;

    @Autowired
    private BinanceClientService binanceClientService;

    @Autowired
    private ICoinBotService iCoinBotService;

    @Autowired
    private ICoinKeysService iCoinKeysService;

    @Autowired
    private ICoinVersionService iCoinVersionService;

    @Autowired
    private ICoinUserService iCoinUserService;

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


    @ApiOperation(value = "新增用户", notes = "查询用户机器人")
    @IgnoreAuth
    @PostMapping(value = "/bot/register")
    public Result<String> register(@RequestBody JSONObject jsonObject) {
        Result<SysUser> result = new Result<SysUser>();
        String selectedRoles = "1894328544810602498";
        String selectedDeparts = "1894327959596142593";
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String invitCode = jsonObject.getString("invitCode");
        SysUser user = new SysUser();
        CoinUser coinUser=new CoinUser();

        try {
            user.setCreateTime(new Date());//设置创建时间
            String salt = oConvertUtils.randomGen(8);
            user.setSalt(salt);
            String passwordEncode = PasswordUtil.encrypt(username, password, salt);
            user.setPassword(passwordEncode);
            user.setStatus(1);
            user.setDelFlag(CommonConstant.DEL_FLAG_0);
            user.setUsername(username);
            //用户表字段org_code不能在这里设置他的值
            user.setOrgCode(null);
            // 保存用户走一个service 保证事务
            //获取租户ids
            String relTenantIds = "1000";
            sysUserService.saveUser(user, selectedRoles, selectedDeparts, relTenantIds);
            baseCommonService.addLog("添加用户，username： " +user.getUsername() ,CommonConstant.LOG_TYPE_2, 2);
            coinUser.setCreateTime(new Date());
            coinUser.setInvited(invitCode);
            coinUser.setMemberId(user.getId());
            iCoinUserService.save(coinUser);
            return Result.ok("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.ok("添加成功！");
        }
    }


}
