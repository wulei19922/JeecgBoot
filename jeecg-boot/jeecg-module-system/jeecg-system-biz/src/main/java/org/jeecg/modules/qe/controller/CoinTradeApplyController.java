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
import org.jeecg.modules.qe.entity.CoinTradeApply;
import org.jeecg.modules.qe.service.ICoinTradeApplyService;

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
 * @Description: 交易提交申请
 * @Author: jeecg-boot
 * @Date:   2025-03-21
 * @Version: V1.0
 */
@Api(tags="交易提交申请")
@RestController
@RequestMapping("/m/coinTradeApply")
@Slf4j
public class CoinTradeApplyController extends JeecgController<CoinTradeApply, ICoinTradeApplyService> {
	@Autowired
	private ICoinTradeApplyService coinTradeApplyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinTradeApply
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "交易提交申请-分页列表查询")
	@ApiOperation(value="交易提交申请-分页列表查询", notes="交易提交申请-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinTradeApply>> queryPageList(CoinTradeApply coinTradeApply,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinTradeApply> queryWrapper = QueryGenerator.initQueryWrapper(coinTradeApply, req.getParameterMap());
		Page<CoinTradeApply> page = new Page<CoinTradeApply>(pageNo, pageSize);
		IPage<CoinTradeApply> pageList = coinTradeApplyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinTradeApply
	 * @return
	 */
	@AutoLog(value = "交易提交申请-添加")
	@ApiOperation(value="交易提交申请-添加", notes="交易提交申请-添加")
	@RequiresPermissions("m:coin_trade_apply:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinTradeApply coinTradeApply) {
		coinTradeApply.setAuditStatus("1");
		coinTradeApplyService.save(coinTradeApply);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinTradeApply
	 * @return
	 */
	@AutoLog(value = "交易提交申请-编辑")
	@ApiOperation(value="交易提交申请-编辑", notes="交易提交申请-编辑")
	@RequiresPermissions("m:coin_trade_apply:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinTradeApply coinTradeApply) {
		coinTradeApplyService.updateById(coinTradeApply);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "交易提交申请-通过id删除")
	@ApiOperation(value="交易提交申请-通过id删除", notes="交易提交申请-通过id删除")
	@RequiresPermissions("m:coin_trade_apply:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinTradeApplyService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "交易提交申请-批量删除")
	@ApiOperation(value="交易提交申请-批量删除", notes="交易提交申请-批量删除")
	@RequiresPermissions("m:coin_trade_apply:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinTradeApplyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "交易提交申请-通过id查询")
	@ApiOperation(value="交易提交申请-通过id查询", notes="交易提交申请-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinTradeApply> queryById(@RequestParam(name="id",required=true) String id) {
		CoinTradeApply coinTradeApply = coinTradeApplyService.getById(id);
		if(coinTradeApply==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinTradeApply);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinTradeApply
    */
    @RequiresPermissions("m:coin_trade_apply:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinTradeApply coinTradeApply) {
        return super.exportXls(request, coinTradeApply, CoinTradeApply.class, "交易提交申请");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("m:coin_trade_apply:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinTradeApply.class);
    }

}
