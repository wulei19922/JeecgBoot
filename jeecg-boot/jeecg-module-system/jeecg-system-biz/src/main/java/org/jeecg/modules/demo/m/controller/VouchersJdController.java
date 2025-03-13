package org.jeecg.modules.demo.m.controller;

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
import org.jeecg.modules.demo.m.entity.VouchersJd;
import org.jeecg.modules.demo.m.service.IVouchersJdService;

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
 * @Description: 京东优惠券规则
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
@Api(tags="京东优惠券规则")
@RestController
@RequestMapping("/m/vouchersJd")
@Slf4j
public class VouchersJdController extends JeecgController<VouchersJd, IVouchersJdService> {
	@Autowired
	private IVouchersJdService vouchersJdService;
	
	/**
	 * 分页列表查询
	 *
	 * @param vouchersJd
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "京东优惠券规则-分页列表查询")
	@ApiOperation(value="京东优惠券规则-分页列表查询", notes="京东优惠券规则-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<VouchersJd>> queryPageList(VouchersJd vouchersJd,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<VouchersJd> queryWrapper = QueryGenerator.initQueryWrapper(vouchersJd, req.getParameterMap());
		Page<VouchersJd> page = new Page<VouchersJd>(pageNo, pageSize);
		IPage<VouchersJd> pageList = vouchersJdService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param vouchersJd
	 * @return
	 */
	@AutoLog(value = "京东优惠券规则-添加")
	@ApiOperation(value="京东优惠券规则-添加", notes="京东优惠券规则-添加")
	@RequiresPermissions("m:vouchers_jd:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody VouchersJd vouchersJd) {
		vouchersJdService.save(vouchersJd);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param vouchersJd
	 * @return
	 */
	@AutoLog(value = "京东优惠券规则-编辑")
	@ApiOperation(value="京东优惠券规则-编辑", notes="京东优惠券规则-编辑")
	@RequiresPermissions("m:vouchers_jd:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody VouchersJd vouchersJd) {
		vouchersJdService.updateById(vouchersJd);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "京东优惠券规则-通过id删除")
	@ApiOperation(value="京东优惠券规则-通过id删除", notes="京东优惠券规则-通过id删除")
	@RequiresPermissions("m:vouchers_jd:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		vouchersJdService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "京东优惠券规则-批量删除")
	@ApiOperation(value="京东优惠券规则-批量删除", notes="京东优惠券规则-批量删除")
	@RequiresPermissions("m:vouchers_jd:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vouchersJdService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "京东优惠券规则-通过id查询")
	@ApiOperation(value="京东优惠券规则-通过id查询", notes="京东优惠券规则-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<VouchersJd> queryById(@RequestParam(name="id",required=true) String id) {
		VouchersJd vouchersJd = vouchersJdService.getById(id);
		if(vouchersJd==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vouchersJd);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vouchersJd
    */
    @RequiresPermissions("m:vouchers_jd:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VouchersJd vouchersJd) {
        return super.exportXls(request, vouchersJd, VouchersJd.class, "京东优惠券规则");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("m:vouchers_jd:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, VouchersJd.class);
    }

}
