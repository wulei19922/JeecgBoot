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
import org.jeecg.modules.qe.entity.CoinVersion;
import org.jeecg.modules.qe.service.ICoinVersionService;

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
 * @Description: coin_version
 * @Author: jeecg-boot
 * @Date:   2025-02-24
 * @Version: V1.0
 */
@Api(tags="coin_version")
@RestController
@RequestMapping("/qe/coinVersion")
@Slf4j
public class CoinVersionController extends JeecgController<CoinVersion, ICoinVersionService> {
	@Autowired
	private ICoinVersionService coinVersionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinVersion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "coin_version-分页列表查询")
	@ApiOperation(value="coin_version-分页列表查询", notes="coin_version-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinVersion>> queryPageList(CoinVersion coinVersion,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinVersion> queryWrapper = QueryGenerator.initQueryWrapper(coinVersion, req.getParameterMap());
		Page<CoinVersion> page = new Page<CoinVersion>(pageNo, pageSize);
		IPage<CoinVersion> pageList = coinVersionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinVersion
	 * @return
	 */
	@AutoLog(value = "coin_version-添加")
	@ApiOperation(value="coin_version-添加", notes="coin_version-添加")
	@RequiresPermissions("qe:coin_version:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinVersion coinVersion) {
		coinVersionService.save(coinVersion);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinVersion
	 * @return
	 */
	@AutoLog(value = "coin_version-编辑")
	@ApiOperation(value="coin_version-编辑", notes="coin_version-编辑")
	@RequiresPermissions("qe:coin_version:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinVersion coinVersion) {
		coinVersionService.updateById(coinVersion);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "coin_version-通过id删除")
	@ApiOperation(value="coin_version-通过id删除", notes="coin_version-通过id删除")
	@RequiresPermissions("qe:coin_version:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinVersionService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "coin_version-批量删除")
	@ApiOperation(value="coin_version-批量删除", notes="coin_version-批量删除")
	@RequiresPermissions("qe:coin_version:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinVersionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "coin_version-通过id查询")
	@ApiOperation(value="coin_version-通过id查询", notes="coin_version-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinVersion> queryById(@RequestParam(name="id",required=true) String id) {
		CoinVersion coinVersion = coinVersionService.getById(id);
		if(coinVersion==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinVersion);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinVersion
    */
    @RequiresPermissions("qe:coin_version:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinVersion coinVersion) {
        return super.exportXls(request, coinVersion, CoinVersion.class, "coin_version");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_version:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinVersion.class);
    }

}
