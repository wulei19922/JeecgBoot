package org.jeecg.modules.qe.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.system.query.QueryRuleEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.HashMap;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.qe.entity.CoinSettlementDayDetail;
import org.jeecg.modules.qe.entity.CoinSettlementDayReward;
import org.jeecg.modules.qe.entity.CoinSettlementDay;
import org.jeecg.modules.qe.service.ICoinSettlementDayService;
import org.jeecg.modules.qe.service.ICoinSettlementDayDetailService;
import org.jeecg.modules.qe.service.ICoinSettlementDayRewardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: 每日结算
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
@Api(tags="每日结算")
@RestController
@RequestMapping("/qe/coinSettlementDay")
@Slf4j
public class CoinSettlementDayController extends JeecgController<CoinSettlementDay, ICoinSettlementDayService> {

	@Autowired
	private ICoinSettlementDayService coinSettlementDayService;

	@Autowired
	private ICoinSettlementDayDetailService coinSettlementDayDetailService;

	@Autowired
	private ICoinSettlementDayRewardService coinSettlementDayRewardService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param coinSettlementDay
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "每日结算-分页列表查询")
	@ApiOperation(value="每日结算-分页列表查询", notes="每日结算-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinSettlementDay>> queryPageList(CoinSettlementDay coinSettlementDay,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
      	QueryWrapper<CoinSettlementDay> queryWrapper = QueryGenerator.initQueryWrapper(coinSettlementDay, req.getParameterMap());
		Page<CoinSettlementDay> page = new Page<CoinSettlementDay>(pageNo, pageSize);
		IPage<CoinSettlementDay> pageList = coinSettlementDayService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param coinSettlementDay
     * @return
     */
    @AutoLog(value = "每日结算-添加")
    @ApiOperation(value="每日结算-添加", notes="每日结算-添加")
    @RequiresPermissions("qe:coin_settlement_day:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CoinSettlementDay coinSettlementDay) {
        coinSettlementDayService.save(coinSettlementDay);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param coinSettlementDay
     * @return
     */
    @AutoLog(value = "每日结算-编辑")
    @ApiOperation(value="每日结算-编辑", notes="每日结算-编辑")
    @RequiresPermissions("qe:coin_settlement_day:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<String> edit(@RequestBody CoinSettlementDay coinSettlementDay) {
        coinSettlementDayService.updateById(coinSettlementDay);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "每日结算-通过id删除")
    @ApiOperation(value="每日结算-通过id删除", notes="每日结算-通过id删除")
    @RequiresPermissions("qe:coin_settlement_day:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name="id",required=true) String id) {
        coinSettlementDayService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "每日结算-批量删除")
    @ApiOperation(value="每日结算-批量删除", notes="每日结算-批量删除")
    @RequiresPermissions("qe:coin_settlement_day:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.coinSettlementDayService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequiresPermissions("qe:coin_settlement_day:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinSettlementDay coinSettlementDay) {
        return super.exportXls(request, coinSettlementDay, CoinSettlementDay.class, "每日结算");
    }

    /**
     * 导入
     * @return
     */
    @RequiresPermissions("qe:coin_settlement_day:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinSettlementDay.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-机器人点数利润明细-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "机器人点数利润明细-通过主表ID查询")
	@ApiOperation(value="机器人点数利润明细-通过主表ID查询", notes="机器人点数利润明细-通过主表ID查询")
	@GetMapping(value = "/listCoinSettlementDayDetailByMainId")
    public Result<IPage<CoinSettlementDayDetail>> listCoinSettlementDayDetailByMainId(CoinSettlementDayDetail coinSettlementDayDetail,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<CoinSettlementDayDetail> queryWrapper = QueryGenerator.initQueryWrapper(coinSettlementDayDetail, req.getParameterMap());
        Page<CoinSettlementDayDetail> page = new Page<CoinSettlementDayDetail>(pageNo, pageSize);
        IPage<CoinSettlementDayDetail> pageList = coinSettlementDayDetailService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param coinSettlementDayDetail
	 * @return
	 */
	@AutoLog(value = "机器人点数利润明细-添加")
	@ApiOperation(value="机器人点数利润明细-添加", notes="机器人点数利润明细-添加")
	@PostMapping(value = "/addCoinSettlementDayDetail")
	public Result<String> addCoinSettlementDayDetail(@RequestBody CoinSettlementDayDetail coinSettlementDayDetail) {
		coinSettlementDayDetailService.save(coinSettlementDayDetail);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param coinSettlementDayDetail
	 * @return
	 */
	@AutoLog(value = "机器人点数利润明细-编辑")
	@ApiOperation(value="机器人点数利润明细-编辑", notes="机器人点数利润明细-编辑")
	@RequestMapping(value = "/editCoinSettlementDayDetail", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editCoinSettlementDayDetail(@RequestBody CoinSettlementDayDetail coinSettlementDayDetail) {
		coinSettlementDayDetailService.updateById(coinSettlementDayDetail);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "机器人点数利润明细-通过id删除")
	@ApiOperation(value="机器人点数利润明细-通过id删除", notes="机器人点数利润明细-通过id删除")
	@DeleteMapping(value = "/deleteCoinSettlementDayDetail")
	public Result<String> deleteCoinSettlementDayDetail(@RequestParam(name="id",required=true) String id) {
		coinSettlementDayDetailService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "机器人点数利润明细-批量删除")
	@ApiOperation(value="机器人点数利润明细-批量删除", notes="机器人点数利润明细-批量删除")
	@DeleteMapping(value = "/deleteBatchCoinSettlementDayDetail")
	public Result<String> deleteBatchCoinSettlementDayDetail(@RequestParam(name="ids",required=true) String ids) {
	    this.coinSettlementDayDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportCoinSettlementDayDetail")
    public ModelAndView exportCoinSettlementDayDetail(HttpServletRequest request, CoinSettlementDayDetail coinSettlementDayDetail) {
		 // Step.1 组装查询条件
		 QueryWrapper<CoinSettlementDayDetail> queryWrapper = QueryGenerator.initQueryWrapper(coinSettlementDayDetail, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<CoinSettlementDayDetail> pageList = coinSettlementDayDetailService.list(queryWrapper);
		 List<CoinSettlementDayDetail> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "机器人点数利润明细");
		 mv.addObject(NormalExcelConstants.CLASS, CoinSettlementDayDetail.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("机器人点数利润明细报表", "导出人:" + sysUser.getRealname(), "机器人点数利润明细"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importCoinSettlementDayDetail/{mainId}")
    public Result<?> importCoinSettlementDayDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
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
				 List<CoinSettlementDayDetail> list = ExcelImportUtil.importExcel(file.getInputStream(), CoinSettlementDayDetail.class, params);
				 for (CoinSettlementDayDetail temp : list) {
                    temp.setHeadId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 coinSettlementDayDetailService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-机器人点数利润明细-end----------------------------------------------*/

    /*--------------------------------子表处理-结算给市场的利润明细-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "结算给市场的利润明细-通过主表ID查询")
	@ApiOperation(value="结算给市场的利润明细-通过主表ID查询", notes="结算给市场的利润明细-通过主表ID查询")
	@GetMapping(value = "/listCoinSettlementDayRewardByMainId")
    public Result<IPage<CoinSettlementDayReward>> listCoinSettlementDayRewardByMainId(CoinSettlementDayReward coinSettlementDayReward,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<CoinSettlementDayReward> queryWrapper = QueryGenerator.initQueryWrapper(coinSettlementDayReward, req.getParameterMap());
        Page<CoinSettlementDayReward> page = new Page<CoinSettlementDayReward>(pageNo, pageSize);
        IPage<CoinSettlementDayReward> pageList = coinSettlementDayRewardService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param coinSettlementDayReward
	 * @return
	 */
	@AutoLog(value = "结算给市场的利润明细-添加")
	@ApiOperation(value="结算给市场的利润明细-添加", notes="结算给市场的利润明细-添加")
	@PostMapping(value = "/addCoinSettlementDayReward")
	public Result<String> addCoinSettlementDayReward(@RequestBody CoinSettlementDayReward coinSettlementDayReward) {
		coinSettlementDayRewardService.save(coinSettlementDayReward);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param coinSettlementDayReward
	 * @return
	 */
	@AutoLog(value = "结算给市场的利润明细-编辑")
	@ApiOperation(value="结算给市场的利润明细-编辑", notes="结算给市场的利润明细-编辑")
	@RequestMapping(value = "/editCoinSettlementDayReward", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editCoinSettlementDayReward(@RequestBody CoinSettlementDayReward coinSettlementDayReward) {
		coinSettlementDayRewardService.updateById(coinSettlementDayReward);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "结算给市场的利润明细-通过id删除")
	@ApiOperation(value="结算给市场的利润明细-通过id删除", notes="结算给市场的利润明细-通过id删除")
	@DeleteMapping(value = "/deleteCoinSettlementDayReward")
	public Result<String> deleteCoinSettlementDayReward(@RequestParam(name="id",required=true) String id) {
		coinSettlementDayRewardService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "结算给市场的利润明细-批量删除")
	@ApiOperation(value="结算给市场的利润明细-批量删除", notes="结算给市场的利润明细-批量删除")
	@DeleteMapping(value = "/deleteBatchCoinSettlementDayReward")
	public Result<String> deleteBatchCoinSettlementDayReward(@RequestParam(name="ids",required=true) String ids) {
	    this.coinSettlementDayRewardService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportCoinSettlementDayReward")
    public ModelAndView exportCoinSettlementDayReward(HttpServletRequest request, CoinSettlementDayReward coinSettlementDayReward) {
		 // Step.1 组装查询条件
		 QueryWrapper<CoinSettlementDayReward> queryWrapper = QueryGenerator.initQueryWrapper(coinSettlementDayReward, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<CoinSettlementDayReward> pageList = coinSettlementDayRewardService.list(queryWrapper);
		 List<CoinSettlementDayReward> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "结算给市场的利润明细");
		 mv.addObject(NormalExcelConstants.CLASS, CoinSettlementDayReward.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("结算给市场的利润明细报表", "导出人:" + sysUser.getRealname(), "结算给市场的利润明细"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importCoinSettlementDayReward/{mainId}")
    public Result<?> importCoinSettlementDayReward(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
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
				 List<CoinSettlementDayReward> list = ExcelImportUtil.importExcel(file.getInputStream(), CoinSettlementDayReward.class, params);
				 for (CoinSettlementDayReward temp : list) {
                    temp.setHeadId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 coinSettlementDayRewardService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-结算给市场的利润明细-end----------------------------------------------*/




}
