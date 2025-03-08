package org.jeecg.modules.qe.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.qe.entity.CoinTrader;
import org.jeecg.modules.qe.service.ICoinTraderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.qe.service.impl.BinanceClientService;
import org.jeecg.modules.qe.service.impl.BinanceWithDrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 平台账户支付记录
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
@Api(tags="平台账户支付记录")
@RestController
@RequestMapping("/qe/coinTrader")
@Slf4j
public class CoinTraderController extends JeecgController<CoinTrader, ICoinTraderService> {
	@Autowired
	private ICoinTraderService coinTraderService;

	@Autowired
	private BinanceWithDrawService withDrawService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinTrader
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "平台账户支付记录-分页列表查询")
	@ApiOperation(value="平台账户支付记录-分页列表查询", notes="平台账户支付记录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinTrader>> queryPageList(CoinTrader coinTrader,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinTrader> queryWrapper = QueryGenerator.initQueryWrapper(coinTrader, req.getParameterMap());
		Page<CoinTrader> page = new Page<CoinTrader>(pageNo, pageSize);
		IPage<CoinTrader> pageList = coinTraderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinTrader
	 * @return
	 */
	@AutoLog(value = "平台账户支付记录-添加")
	@ApiOperation(value="平台账户支付记录-添加", notes="平台账户支付记录-添加")
	@RequiresPermissions("qe:coin_trader:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinTrader coinTrader) {
		coinTraderService.save(coinTrader);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinTrader
	 * @return
	 */
	@AutoLog(value = "平台账户支付记录-编辑")
	@ApiOperation(value="平台账户支付记录-编辑", notes="平台账户支付记录-编辑")
	@RequiresPermissions("qe:coin_trader:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinTrader coinTrader) {
		coinTraderService.updateById(coinTrader);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "平台账户支付记录-通过id删除")
	@ApiOperation(value="平台账户支付记录-通过id删除", notes="平台账户支付记录-通过id删除")
	@RequiresPermissions("qe:coin_trader:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinTraderService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "平台账户支付记录-批量删除")
	@ApiOperation(value="平台账户支付记录-批量删除", notes="平台账户支付记录-批量删除")
	@RequiresPermissions("qe:coin_trader:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinTraderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 *  批量支付
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "平台账户支付记录-支付")
	@ApiOperation(value="平台账户支付记录-支付", notes="平台账户支付记录-支付")
//	@RequiresPermissions("qe:coin_trader:deleteBatch")
	@RequestMapping(value = "/pay",method = {RequestMethod.POST,RequestMethod.PUT})
	public Result<String> payBatch(@RequestParam(name="ids",required=true) String ids) {
//		this.coinTraderService.removeByIds(Arrays.asList(ids.split(",")));
		boolean payres=withDrawService.payList(ids);
		return Result.OK("批量支付成功!");
	}

	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "平台账户支付记录-通过id查询")
	@ApiOperation(value="平台账户支付记录-通过id查询", notes="平台账户支付记录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinTrader> queryById(@RequestParam(name="id",required=true) String id) {
		CoinTrader coinTrader = coinTraderService.getById(id);
		if(coinTrader==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinTrader);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinTrader
    */
    @RequiresPermissions("qe:coin_trader:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinTrader coinTrader) {
        return super.exportXls(request, coinTrader, CoinTrader.class, "平台账户支付记录");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_trader:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinTrader.class);
    }

}
