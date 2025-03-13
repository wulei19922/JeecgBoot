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
import org.jeecg.modules.demo.m.entity.VouchersWechat;
import org.jeecg.modules.demo.m.service.IVouchersWechatService;

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
 * @Description: 微信优惠券规则
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
@Api(tags="微信优惠券规则")
@RestController
@RequestMapping("/m/vouchersWechat")
@Slf4j
public class VouchersWechatController extends JeecgController<VouchersWechat, IVouchersWechatService> {
	@Autowired
	private IVouchersWechatService vouchersWechatService;
	
	/**
	 * 分页列表查询
	 *
	 * @param vouchersWechat
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "微信优惠券规则-分页列表查询")
	@ApiOperation(value="微信优惠券规则-分页列表查询", notes="微信优惠券规则-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<VouchersWechat>> queryPageList(VouchersWechat vouchersWechat,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<VouchersWechat> queryWrapper = QueryGenerator.initQueryWrapper(vouchersWechat, req.getParameterMap());
		Page<VouchersWechat> page = new Page<VouchersWechat>(pageNo, pageSize);
		IPage<VouchersWechat> pageList = vouchersWechatService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param vouchersWechat
	 * @return
	 */
	@AutoLog(value = "微信优惠券规则-添加")
	@ApiOperation(value="微信优惠券规则-添加", notes="微信优惠券规则-添加")
	@RequiresPermissions("m:vouchers_wechat:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody VouchersWechat vouchersWechat) {
		vouchersWechatService.save(vouchersWechat);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param vouchersWechat
	 * @return
	 */
	@AutoLog(value = "微信优惠券规则-编辑")
	@ApiOperation(value="微信优惠券规则-编辑", notes="微信优惠券规则-编辑")
	@RequiresPermissions("m:vouchers_wechat:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody VouchersWechat vouchersWechat) {
		vouchersWechatService.updateById(vouchersWechat);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "微信优惠券规则-通过id删除")
	@ApiOperation(value="微信优惠券规则-通过id删除", notes="微信优惠券规则-通过id删除")
	@RequiresPermissions("m:vouchers_wechat:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		vouchersWechatService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "微信优惠券规则-批量删除")
	@ApiOperation(value="微信优惠券规则-批量删除", notes="微信优惠券规则-批量删除")
	@RequiresPermissions("m:vouchers_wechat:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vouchersWechatService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "微信优惠券规则-通过id查询")
	@ApiOperation(value="微信优惠券规则-通过id查询", notes="微信优惠券规则-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<VouchersWechat> queryById(@RequestParam(name="id",required=true) String id) {
		VouchersWechat vouchersWechat = vouchersWechatService.getById(id);
		if(vouchersWechat==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vouchersWechat);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vouchersWechat
    */
    @RequiresPermissions("m:vouchers_wechat:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VouchersWechat vouchersWechat) {
        return super.exportXls(request, vouchersWechat, VouchersWechat.class, "微信优惠券规则");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("m:vouchers_wechat:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, VouchersWechat.class);
    }

}
