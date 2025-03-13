package org.jeecg.modules.demo.m.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.demo.m.entity.VouchersWechat;
import org.jeecg.modules.demo.m.service.IVouchersWechatService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.m.entity.MarketVouchersMerchants;
import org.jeecg.modules.demo.m.entity.MarketVouchers;
import org.jeecg.modules.demo.m.vo.MarketVouchersPage;
import org.jeecg.modules.demo.m.service.IMarketVouchersService;
import org.jeecg.modules.demo.m.service.IMarketVouchersMerchantsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


 /**
 * @Description: 支付营销代金券场景
 * @Author: jeecg-boot
 * @Date:   2025-03-11
 * @Version: V1.0
 */
@Api(tags="支付营销代金券场景")
@RestController
@RequestMapping("/m/marketVouchers")
@Slf4j
public class MarketVouchersController {
	@Autowired
	private IMarketVouchersService marketVouchersService;
	@Autowired
	private IMarketVouchersMerchantsService marketVouchersMerchantsService;

	@Autowired
	private IVouchersWechatService wechatService;

	/**
	 * 分页列表查询
	 *
	 * @param marketVouchers
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "支付营销代金券场景-分页列表查询")
	@ApiOperation(value="支付营销代金券场景-分页列表查询", notes="支付营销代金券场景-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<MarketVouchers>> queryPageList(MarketVouchers marketVouchers,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<MarketVouchers> queryWrapper = QueryGenerator.initQueryWrapper(marketVouchers, req.getParameterMap());
		Page<MarketVouchers> page = new Page<MarketVouchers>(pageNo, pageSize);
		IPage<MarketVouchers> pageList = marketVouchersService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param marketVouchersPage
	 * @return
	 */
	@AutoLog(value = "支付营销代金券场景-添加")
	@ApiOperation(value="支付营销代金券场景-添加", notes="支付营销代金券场景-添加")
    @RequiresPermissions("m:market_vouchers:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody MarketVouchersPage marketVouchersPage) {

		// 先初始化规则模板
		// 1 查询模板
		// 2 根据模板生成数据
		VouchersWechat templateWechat = wechatService.getById("1900090936140922882");
		templateWechat.setId("");
		templateWechat.setStockName(marketVouchersPage.getName());
		templateWechat.setComment(marketVouchersPage.getRemark());
		wechatService.save(templateWechat);

		MarketVouchers marketVouchers = new MarketVouchers();
		BeanUtils.copyProperties(marketVouchersPage, marketVouchers);
		marketVouchers.setSettingId(templateWechat.getId());
		marketVouchers.setSettingTable(marketVouchersPage.getPlatform());
		marketVouchersService.saveMain(marketVouchers, marketVouchersPage.getMarketVouchersMerchantsList());


		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param marketVouchersPage
	 * @return
	 */
	@AutoLog(value = "支付营销代金券场景-编辑")
	@ApiOperation(value="支付营销代金券场景-编辑", notes="支付营销代金券场景-编辑")
    @RequiresPermissions("m:market_vouchers:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody MarketVouchersPage marketVouchersPage) {
		MarketVouchers marketVouchers = new MarketVouchers();
		BeanUtils.copyProperties(marketVouchersPage, marketVouchers);
		MarketVouchers marketVouchersEntity = marketVouchersService.getById(marketVouchers.getId());
		if(marketVouchersEntity==null) {
			return Result.error("未找到对应数据");
		}
		marketVouchersService.updateMain(marketVouchers, marketVouchersPage.getMarketVouchersMerchantsList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "支付营销代金券场景-通过id删除")
	@ApiOperation(value="支付营销代金券场景-通过id删除", notes="支付营销代金券场景-通过id删除")
    @RequiresPermissions("m:market_vouchers:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		marketVouchersService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "支付营销代金券场景-批量删除")
	@ApiOperation(value="支付营销代金券场景-批量删除", notes="支付营销代金券场景-批量删除")
    @RequiresPermissions("m:market_vouchers:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.marketVouchersService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "支付营销代金券场景-通过id查询")
	@ApiOperation(value="支付营销代金券场景-通过id查询", notes="支付营销代金券场景-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<MarketVouchers> queryById(@RequestParam(name="id",required=true) String id) {
		MarketVouchers marketVouchers = marketVouchersService.getById(id);
		if(marketVouchers==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(marketVouchers);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "代金券场景归属商户通过主表ID查询")
	@ApiOperation(value="代金券场景归属商户主表ID查询", notes="代金券场景归属商户-通主表ID查询")
	@GetMapping(value = "/queryMarketVouchersMerchantsByMainId")
	public Result<List<MarketVouchersMerchants>> queryMarketVouchersMerchantsListByMainId(@RequestParam(name="id",required=true) String id) {
		List<MarketVouchersMerchants> marketVouchersMerchantsList = marketVouchersMerchantsService.selectByMainId(id);
		return Result.OK(marketVouchersMerchantsList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param marketVouchers
    */
    @RequiresPermissions("m:market_vouchers:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MarketVouchers marketVouchers) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<MarketVouchers> queryWrapper = QueryGenerator.initQueryWrapper(marketVouchers, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<MarketVouchers> marketVouchersList = marketVouchersService.list(queryWrapper);

      // Step.3 组装pageList
      List<MarketVouchersPage> pageList = new ArrayList<MarketVouchersPage>();
      for (MarketVouchers main : marketVouchersList) {
          MarketVouchersPage vo = new MarketVouchersPage();
          BeanUtils.copyProperties(main, vo);
          List<MarketVouchersMerchants> marketVouchersMerchantsList = marketVouchersMerchantsService.selectByMainId(main.getId());
          vo.setMarketVouchersMerchantsList(marketVouchersMerchantsList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "支付营销代金券场景列表");
      mv.addObject(NormalExcelConstants.CLASS, MarketVouchersPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("支付营销代金券场景数据", "导出人:"+sysUser.getRealname(), "支付营销代金券场景"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("m:market_vouchers:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<MarketVouchersPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MarketVouchersPage.class, params);
              for (MarketVouchersPage page : list) {
                  MarketVouchers po = new MarketVouchers();
                  BeanUtils.copyProperties(page, po);
                  marketVouchersService.saveMain(po, page.getMarketVouchersMerchantsList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
