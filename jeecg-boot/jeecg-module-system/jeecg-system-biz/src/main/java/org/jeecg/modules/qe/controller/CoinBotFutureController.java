package org.jeecg.modules.qe.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.qe.entity.CoinBotFuture;
import org.jeecg.modules.qe.service.ICoinBotFutureService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.qe.service.impl.BinanceFuturesService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description: 机器人合约表
 * @Author: jeecg-boot
 * @Date:   2025-04-15
 * @Version: V1.0
 */
@Api(tags="机器人合约表")
@RestController
@RequestMapping("/qe/coinBotFuture")
@Slf4j
public class CoinBotFutureController extends JeecgController<CoinBotFuture, ICoinBotFutureService> {
	@Autowired
	private ICoinBotFutureService coinBotFutureService;

	@Autowired
	private BinanceFuturesService binanceFuturesService;

	/**
	 * 分页列表查询
	 *
	 * @param coinBotFuture
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "机器人合约表-分页列表查询")
	@ApiOperation(value="机器人合约表-分页列表查询", notes="机器人合约表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinBotFuture>> queryPageList(CoinBotFuture coinBotFuture,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinBotFuture> queryWrapper = QueryGenerator.initQueryWrapper(coinBotFuture, req.getParameterMap());
		Page<CoinBotFuture> page = new Page<CoinBotFuture>(pageNo, pageSize);
		IPage<CoinBotFuture> pageList = coinBotFutureService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	@ApiOperation(value="机器人合约表-币安平台列表", notes="机器人合约表-币安平台列表")
	@GetMapping(value = "/binance/list")
	public Result<Object> queryPageListBinance(@RequestParam(name="botId") String  botId,@RequestParam(name="type") String  type,
								   HttpServletRequest req) {
		if (type.equals("postion")){
			JSONArray futuresPostions = binanceFuturesService.getFuturesPostions(botId);
			return Result.OK(futuresPostions);
		}
		else if (type.equals("openorders")){
			List<CoinBotFuture> futuresPostions = binanceFuturesService.getFuturesOpenOrders(botId);
			return Result.OK(futuresPostions);
		}else{
			return  null;
		}
	}
	 @ApiOperation(value="机器人合约表-获得账户余额", notes="机器人合约表-获得账户余额")
	 @GetMapping(value = "/binance/wallet")
	 public Result<Object> getWallet(@RequestParam("userId")String userId) {
		 JSONObject futuresPostions = binanceFuturesService.getAssetInfo(userId);
		 return  Result.OK(futuresPostions);
	 }

	 @ApiOperation(value="机器人合约表-获得账户余额", notes="机器人合约表-获得账户余额")
	 @GetMapping(value = "/binance/incomelist")
	 public Result<Object> getIncomeList(@RequestParam("userId")String userId,@RequestParam("symbol")String symbol,@RequestParam("day")Long  day) {
		 JSONArray futuresPostions = binanceFuturesService.getIncomeList(userId,symbol,day);
		 return  Result.OK(futuresPostions);
	 }

	 @ApiOperation(value="机器人合约表-获得账户余额", notes="机器人合约表-获得账户余额")
	 @PostMapping(value = "/binance/transfer")
	 public Result<Object> transferWallet(@RequestBody JSONObject params) {
		 String userId = params.getString("userId");
		 String amount = params.getString("amount");
		 String type = params.getString("type");
		 Result<Object> r = binanceFuturesService.transferFromFuturesToSpot(userId,"USDT", amount,type);
		 return  r;
	 }

	 @ApiOperation(value="机器人合约表-创建订单", notes="机器人合约表-创建订单")
	 @GetMapping(value = "/binance/create")
	 public Result<JSONArray> createOrder(@RequestBody JSONObject jsonObject) {
		 String type = jsonObject.getString("type");
		 if (type.equals("create")){
//			 JSONArray futuresPostions = binanceFuturesService.createOrder(jsonObject);
//			 return Result.OK(futuresPostions);
		 }else if (type.equals("edit")){

			 return null;
		 }else if (type.equals("list")){
			//查询持仓
			 return null;
		 }
		 else if (type.equals("cancel")){

			 return null;
		 }
		 return null;
	 }
	
	/**
	 *   添加
	 *
	 * @param coinBotFuture
	 * @return
	 */
	@AutoLog(value = "机器人合约表-添加")
	@ApiOperation(value="机器人合约表-添加", notes="机器人合约表-添加")
	@RequiresPermissions("qe:coin_bot_future:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinBotFuture coinBotFuture) {

		//先检查 该symbol是否是否已经存在同个方向的委托
		QueryWrapper<CoinBotFuture>queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("symbol",coinBotFuture.getSymbol());
		queryWrapper.eq("silde",coinBotFuture.getSilde());
		queryWrapper.eq("bot_id",coinBotFuture.getBotId());
		queryWrapper.eq("order_status","NEW");
		List<CoinBotFuture> coinBotFutures = coinBotFutureService.list(queryWrapper);
		if (coinBotFutures.size()>0){
			return Result.OK("该方向的委托已经存在！");
		}

		coinBotFuture=binanceFuturesService.createOrder(coinBotFuture);
		//初始状态为新建状态
		coinBotFuture.setOrderStatus("NEW");
		coinBotFuture.setExchange("BINANCE");
		coinBotFutureService.save(coinBotFuture);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinBotFuture
	 * @return
	 */
	@AutoLog(value = "机器人合约表-编辑")
	@ApiOperation(value="机器人合约表-编辑", notes="机器人合约表-编辑")
	@RequiresPermissions("qe:coin_bot_future:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinBotFuture coinBotFuture) {
		coinBotFutureService.updateById(coinBotFuture);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "机器人合约表-通过id删除")
	@ApiOperation(value="机器人合约表-通过id删除", notes="机器人合约表-通过id删除")
	@RequiresPermissions("qe:coin_bot_future:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinBotFutureService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "机器人合约表-批量删除")
	@ApiOperation(value="机器人合约表-批量删除", notes="机器人合约表-批量删除")
	@RequiresPermissions("qe:coin_bot_future:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinBotFutureService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "机器人合约表-通过id查询")
	@ApiOperation(value="机器人合约表-通过id查询", notes="机器人合约表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinBotFuture> queryById(@RequestParam(name="id",required=true) String id) {
		CoinBotFuture coinBotFuture = coinBotFutureService.getById(id);
		if(coinBotFuture==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinBotFuture);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinBotFuture
    */
    @RequiresPermissions("qe:coin_bot_future:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinBotFuture coinBotFuture) {
        return super.exportXls(request, coinBotFuture, CoinBotFuture.class, "机器人合约表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_bot_future:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinBotFuture.class);
    }

}
