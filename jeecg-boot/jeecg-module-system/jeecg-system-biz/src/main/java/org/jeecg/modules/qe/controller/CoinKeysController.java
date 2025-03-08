package org.jeecg.modules.qe.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.qe.entity.CoinKeys;
import org.jeecg.modules.qe.entity.CoinWallet;
import org.jeecg.modules.qe.service.ICoinKeysService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.qe.service.ICoinWalletService;
import org.jeecg.modules.qe.service.impl.BinanceWithDrawService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 用户平台密钥对
 * @Author: jeecg-boot
 * @Date:   2025-02-24
 * @Version: V1.0
 */
@Api(tags="用户平台密钥对")
@RestController
@RequestMapping("/qe/coinKeys")
@Slf4j
public class CoinKeysController extends JeecgController<CoinKeys, ICoinKeysService> {
	@Autowired
	private ICoinKeysService coinKeysService;

	@Autowired

	private BinanceWithDrawService binanceWithDrawService;

	@Autowired
	private ICoinWalletService coinWalletService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinKeys
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户平台密钥对-分页列表查询")
	@ApiOperation(value="用户平台密钥对-分页列表查询", notes="用户平台密钥对-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinKeys>> queryPageList(CoinKeys coinKeys,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinKeys> queryWrapper = QueryGenerator.initQueryWrapper(coinKeys, req.getParameterMap());
		Page<CoinKeys> page = new Page<CoinKeys>(pageNo, pageSize);
		IPage<CoinKeys> pageList = coinKeysService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 @ApiOperation(value="用户平台密钥对-分页列表查询", notes="用户平台密钥对-分页列表查询")
	 @GetMapping(value = "/mylist")
	 public Result<CoinKeys> queryMyPageList(CoinKeys coinKeys,
												  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												  HttpServletRequest req) {

		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 QueryWrapper<CoinKeys> queryWrapper = QueryGenerator.initQueryWrapper(coinKeys, req.getParameterMap());
		 queryWrapper.eq("member_id",sysUser.getId());
		 queryWrapper.eq("exchange","BINANCE");
		 queryWrapper.eq("env","prod");

		 List<CoinKeys> list = coinKeysService.list(queryWrapper);
		 if (!list.isEmpty()){
			 //前段加密显示
			 CoinKeys coinKeys1 = list.get(0);
			 coinKeys1.setApiKey("******************");
			 coinKeys1.setApiSecret("******************");
			 return Result.OK(coinKeys1);
		 }else{
			 return Result.OK(null);
		 }
	 }
	
	/**
	 *   添加
	 *
	 * @param coinKeys
	 * @return
	 */
	@AutoLog(value = "用户平台密钥对-添加")
	@ApiOperation(value="用户平台密钥对-添加", notes="用户平台密钥对-添加")
	@RequiresPermissions("qe:coin_keys:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinKeys coinKeys) {
		coinKeysService.save(coinKeys);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinKeys
	 * @return
	 */
	@AutoLog(value = "用户平台密钥对-编辑")
	@ApiOperation(value="用户平台密钥对-编辑", notes="用户平台密钥对-编辑")
	@RequiresPermissions("qe:coin_keys:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinKeys coinKeys) {
		coinKeysService.updateById(coinKeys);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户平台密钥对-通过id删除")
	@ApiOperation(value="用户平台密钥对-通过id删除", notes="用户平台密钥对-通过id删除")
	@RequiresPermissions("qe:coin_keys:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinKeysService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户平台密钥对-批量删除")
	@ApiOperation(value="用户平台密钥对-批量删除", notes="用户平台密钥对-批量删除")
	@RequiresPermissions("qe:coin_keys:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinKeysService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户平台密钥对-通过id查询")
	@ApiOperation(value="用户平台密钥对-通过id查询", notes="用户平台密钥对-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinKeys> queryById(@RequestParam(name="id",required=true) String id) {
		CoinKeys coinKeys = coinKeysService.getById(id);
		if(coinKeys==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinKeys);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinKeys
    */
    @RequiresPermissions("qe:coin_keys:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinKeys coinKeys) {
        return super.exportXls(request, coinKeys, CoinKeys.class, "用户平台密钥对");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_keys:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinKeys.class);
    }


	 @AutoLog(value = "用户平台密钥对-添加")
	 @ApiOperation(value="用户平台密钥对-添加", notes="用户平台密钥对-添加")
//	 @RequiresPermissions("qe:coin_keys:add")
	 @PostMapping(value = "/useradd")
	 public Result<String> useradd(@RequestBody CoinKeys coinKeys) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 //**教研key是否有效
		 //检查是否初始化用户钱包
		 CoinWallet coinWallet = binanceWithDrawService.getCoinWallet(sysUser.getId());
		 Double  free = binanceWithDrawService.getWallet(sysUser.getId(),"BINANCE",coinKeys.getApiKey(),coinKeys.getApiSecret());
		 String uid  = binanceWithDrawService.getUid(sysUser.getId(),"BINANCE",coinKeys.getApiKey(),coinKeys.getApiSecret());
		 String address  = binanceWithDrawService.getChargeAddress(coinKeys.getApiKey(),coinKeys.getApiSecret());

		 if (free>=0D){
			 if (coinWallet!=null){
				 coinWallet.setFree(free);
				 coinWallet.setUpdateBy(sysUser.getUsername());
				 coinWallet.setUpdateTime(new Date());
				 coinWalletService.updateById(coinWallet);
				 //同步钱包数据
			 }else{
				 CoinWallet wallet=new CoinWallet();
				 wallet.setFree(0D);
				 wallet.setSymbol("USDT");
				 wallet.setExchange("BINANCE");
				 wallet.setLocked(0D);
				 wallet.setCreateBy(sysUser.getUsername());
				 wallet.setCreateTime(new Date());
				 wallet.setDayProfit(0D);
				 wallet.setMemberId(sysUser.getId());
				 coinWalletService.save(wallet);
				 //初始化钱包
			 }
			 if(StringUtils.hasText(coinKeys.getId())){
				 if(coinKeys.getApiKey().equals("******************")){
					 coinKeys.setApiKey(null);
					 coinKeys.setApiSecret(null);
				 }
				 coinKeys.setUid(uid);
				 coinKeys.setAddress(address);
				 coinKeysService.updateById(coinKeys);
			 }else{
				 coinKeys.setEnv("prod");
				 coinKeys.setExchange("BINANCE");
				 coinKeys.setCreateTime(new Date());
				 coinKeys.setMemberId(sysUser.getId());
				 coinKeys.setCreateBy(sysUser.getUsername());
				 coinKeys.setUid(uid);
				 coinKeysService.save(coinKeys);
			 }


			 return Result.OK("添加成功！");

		 }else{
			 return Result.error("KEY无效");

		 }





	 }

}
