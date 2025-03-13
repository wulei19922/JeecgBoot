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
import org.jeecg.modules.demo.m.entity.VouchersAlipay;
import org.jeecg.modules.demo.m.service.IVouchersAlipayService;

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
 * @Description: 阿里优惠券规则
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
@Api(tags="阿里优惠券规则")
@RestController
@RequestMapping("/m/vouchersAlipay")
@Slf4j
public class VouchersAlipayController extends JeecgController<VouchersAlipay, IVouchersAlipayService> {
	@Autowired
	private IVouchersAlipayService vouchersAlipayService;
	
	/**
	 * 分页列表查询
	 *
	 * @param vouchersAlipay
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "阿里优惠券规则-分页列表查询")
	@ApiOperation(value="阿里优惠券规则-分页列表查询", notes="阿里优惠券规则-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<VouchersAlipay>> queryPageList(VouchersAlipay vouchersAlipay,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<VouchersAlipay> queryWrapper = QueryGenerator.initQueryWrapper(vouchersAlipay, req.getParameterMap());
		Page<VouchersAlipay> page = new Page<VouchersAlipay>(pageNo, pageSize);
		IPage<VouchersAlipay> pageList = vouchersAlipayService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param vouchersAlipay
	 * @return
	 */
	@AutoLog(value = "阿里优惠券规则-添加")
	@ApiOperation(value="阿里优惠券规则-添加", notes="阿里优惠券规则-添加")
	@RequiresPermissions("m:vouchers_alipay:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody VouchersAlipay vouchersAlipay) {
		vouchersAlipayService.save(vouchersAlipay);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param vouchersAlipay
	 * @return
	 */
	@AutoLog(value = "阿里优惠券规则-编辑")
	@ApiOperation(value="阿里优惠券规则-编辑", notes="阿里优惠券规则-编辑")
	@RequiresPermissions("m:vouchers_alipay:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody VouchersAlipay vouchersAlipay) {
		vouchersAlipayService.updateById(vouchersAlipay);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "阿里优惠券规则-通过id删除")
	@ApiOperation(value="阿里优惠券规则-通过id删除", notes="阿里优惠券规则-通过id删除")
	@RequiresPermissions("m:vouchers_alipay:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		vouchersAlipayService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "阿里优惠券规则-批量删除")
	@ApiOperation(value="阿里优惠券规则-批量删除", notes="阿里优惠券规则-批量删除")
	@RequiresPermissions("m:vouchers_alipay:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vouchersAlipayService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "阿里优惠券规则-通过id查询")
	@ApiOperation(value="阿里优惠券规则-通过id查询", notes="阿里优惠券规则-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<VouchersAlipay> queryById(@RequestParam(name="id",required=true) String id) {
		VouchersAlipay vouchersAlipay = vouchersAlipayService.getById(id);
		if(vouchersAlipay==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vouchersAlipay);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vouchersAlipay
    */
    @RequiresPermissions("m:vouchers_alipay:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VouchersAlipay vouchersAlipay) {
        return super.exportXls(request, vouchersAlipay, VouchersAlipay.class, "阿里优惠券规则");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("m:vouchers_alipay:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, VouchersAlipay.class);
    }

}
