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
import org.jeecg.modules.qe.entity.CoinQe;
import org.jeecg.modules.qe.service.ICoinQeService;

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
 * @Description: 首页广告
 * @Author: jeecg-boot
 * @Date:   2025-02-23
 * @Version: V1.0
 */
@Api(tags="首页广告")
@RestController
@RequestMapping("/qe/coinQe")
@Slf4j
public class CoinQeController extends JeecgController<CoinQe, ICoinQeService> {
	@Autowired
	private ICoinQeService coinQeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinQe
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "首页广告-分页列表查询")
	@ApiOperation(value="首页广告-分页列表查询", notes="首页广告-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinQe>> queryPageList(CoinQe coinQe,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinQe> queryWrapper = QueryGenerator.initQueryWrapper(coinQe, req.getParameterMap());
		Page<CoinQe> page = new Page<CoinQe>(pageNo, pageSize);
		IPage<CoinQe> pageList = coinQeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinQe
	 * @return
	 */
	@AutoLog(value = "首页广告-添加")
	@ApiOperation(value="首页广告-添加", notes="首页广告-添加")
	@RequiresPermissions("qe:coin_qe:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinQe coinQe) {
		coinQeService.save(coinQe);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinQe
	 * @return
	 */
	@AutoLog(value = "首页广告-编辑")
	@ApiOperation(value="首页广告-编辑", notes="首页广告-编辑")
	@RequiresPermissions("qe:coin_qe:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinQe coinQe) {
		coinQeService.updateById(coinQe);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "首页广告-通过id删除")
	@ApiOperation(value="首页广告-通过id删除", notes="首页广告-通过id删除")
	@RequiresPermissions("qe:coin_qe:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinQeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "首页广告-批量删除")
	@ApiOperation(value="首页广告-批量删除", notes="首页广告-批量删除")
	@RequiresPermissions("qe:coin_qe:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinQeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "首页广告-通过id查询")
	@ApiOperation(value="首页广告-通过id查询", notes="首页广告-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinQe> queryById(@RequestParam(name="id",required=true) String id) {
		CoinQe coinQe = coinQeService.getById(id);
		if(coinQe==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinQe);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinQe
    */
    @RequiresPermissions("qe:coin_qe:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinQe coinQe) {
        return super.exportXls(request, coinQe, CoinQe.class, "首页广告");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_qe:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinQe.class);
    }

}
