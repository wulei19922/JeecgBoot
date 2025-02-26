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
import org.jeecg.modules.qe.entity.CoinUser;
import org.jeecg.modules.qe.service.ICoinUserService;

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
 * @Description: 推荐关系
 * @Author: jeecg-boot
 * @Date:   2025-02-25
 * @Version: V1.0
 */
@Api(tags="推荐关系")
@RestController
@RequestMapping("/qe/coinUser")
@Slf4j
public class CoinUserController extends JeecgController<CoinUser, ICoinUserService> {
	@Autowired
	private ICoinUserService coinUserService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "推荐关系-分页列表查询")
	@ApiOperation(value="推荐关系-分页列表查询", notes="推荐关系-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinUser>> queryPageList(CoinUser coinUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinUser> queryWrapper = QueryGenerator.initQueryWrapper(coinUser, req.getParameterMap());
		Page<CoinUser> page = new Page<CoinUser>(pageNo, pageSize);
		IPage<CoinUser> pageList = coinUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinUser
	 * @return
	 */
	@AutoLog(value = "推荐关系-添加")
	@ApiOperation(value="推荐关系-添加", notes="推荐关系-添加")
	@RequiresPermissions("qe:coin_user:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinUser coinUser) {
		coinUserService.save(coinUser);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinUser
	 * @return
	 */
	@AutoLog(value = "推荐关系-编辑")
	@ApiOperation(value="推荐关系-编辑", notes="推荐关系-编辑")
	@RequiresPermissions("qe:coin_user:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinUser coinUser) {
		coinUserService.updateById(coinUser);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "推荐关系-通过id删除")
	@ApiOperation(value="推荐关系-通过id删除", notes="推荐关系-通过id删除")
	@RequiresPermissions("qe:coin_user:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinUserService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "推荐关系-批量删除")
	@ApiOperation(value="推荐关系-批量删除", notes="推荐关系-批量删除")
	@RequiresPermissions("qe:coin_user:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "推荐关系-通过id查询")
	@ApiOperation(value="推荐关系-通过id查询", notes="推荐关系-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinUser> queryById(@RequestParam(name="id",required=true) String id) {
		CoinUser coinUser = coinUserService.getById(id);
		if(coinUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinUser
    */
    @RequiresPermissions("qe:coin_user:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinUser coinUser) {
        return super.exportXls(request, coinUser, CoinUser.class, "推荐关系");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_user:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinUser.class);
    }

}
