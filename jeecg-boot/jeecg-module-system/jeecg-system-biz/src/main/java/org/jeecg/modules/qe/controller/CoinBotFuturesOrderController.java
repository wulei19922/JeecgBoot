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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.qe.entity.CoinBotFuturesOrder;
import org.jeecg.modules.qe.service.ICoinBotFuturesOrderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 机器人合约持仓表
 * @Author: jeecg-boot
 * @Date:   2025-04-15
 * @Version: V1.0
 */
@Api(tags="机器人合约持仓表")
@RestController
@RequestMapping("/qe/coinBotFuturesOrder")
@Slf4j
public class CoinBotFuturesOrderController extends JeecgController<CoinBotFuturesOrder, ICoinBotFuturesOrderService> {
	@Autowired
	private ICoinBotFuturesOrderService coinBotFuturesOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinBotFuturesOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "机器人合约持仓表-分页列表查询")
	@ApiOperation(value="机器人合约持仓表-分页列表查询", notes="机器人合约持仓表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinBotFuturesOrder>> queryPageList(CoinBotFuturesOrder coinBotFuturesOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinBotFuturesOrder> queryWrapper = QueryGenerator.initQueryWrapper(coinBotFuturesOrder, req.getParameterMap());
		Page<CoinBotFuturesOrder> page = new Page<CoinBotFuturesOrder>(pageNo, pageSize);
		IPage<CoinBotFuturesOrder> pageList = coinBotFuturesOrderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinBotFuturesOrder
	 * @return
	 */
	@AutoLog(value = "机器人合约持仓表-添加")
	@ApiOperation(value="机器人合约持仓表-添加", notes="机器人合约持仓表-添加")
	@RequiresPermissions("qe:coin_bot_futures_order:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinBotFuturesOrder coinBotFuturesOrder) {
		coinBotFuturesOrderService.save(coinBotFuturesOrder);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinBotFuturesOrder
	 * @return
	 */
	@AutoLog(value = "机器人合约持仓表-编辑")
	@ApiOperation(value="机器人合约持仓表-编辑", notes="机器人合约持仓表-编辑")
	@RequiresPermissions("qe:coin_bot_futures_order:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinBotFuturesOrder coinBotFuturesOrder) {
		coinBotFuturesOrderService.updateById(coinBotFuturesOrder);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "机器人合约持仓表-通过id删除")
	@ApiOperation(value="机器人合约持仓表-通过id删除", notes="机器人合约持仓表-通过id删除")
	@RequiresPermissions("qe:coin_bot_futures_order:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinBotFuturesOrderService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "机器人合约持仓表-批量删除")
	@ApiOperation(value="机器人合约持仓表-批量删除", notes="机器人合约持仓表-批量删除")
	@RequiresPermissions("qe:coin_bot_futures_order:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinBotFuturesOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "机器人合约持仓表-通过id查询")
	@ApiOperation(value="机器人合约持仓表-通过id查询", notes="机器人合约持仓表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinBotFuturesOrder> queryById(@RequestParam(name="id",required=true) String id) {
		CoinBotFuturesOrder coinBotFuturesOrder = coinBotFuturesOrderService.getById(id);
		if(coinBotFuturesOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinBotFuturesOrder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinBotFuturesOrder
    */
    @RequiresPermissions("qe:coin_bot_futures_order:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinBotFuturesOrder coinBotFuturesOrder) {
        return super.exportXls(request, coinBotFuturesOrder, CoinBotFuturesOrder.class, "机器人合约持仓表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_bot_futures_order:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinBotFuturesOrder.class);
    }

}
