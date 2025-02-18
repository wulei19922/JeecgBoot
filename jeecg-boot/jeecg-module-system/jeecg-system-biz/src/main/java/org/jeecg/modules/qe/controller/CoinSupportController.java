package org.jeecg.modules.qe.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.qe.entity.CoinSupport;
import org.jeecg.modules.qe.service.ICoinSupportService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 支持的两户货币
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Api(tags="支持的两户货币")
@RestController
@RequestMapping("/qe/coinSupport")
@Slf4j
public class CoinSupportController extends JeecgController<CoinSupport, ICoinSupportService> {
	@Autowired
	private ICoinSupportService coinSupportService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinSupport
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "支持的两户货币-分页列表查询")
	@ApiOperation(value="支持的两户货币-分页列表查询", notes="支持的两户货币-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinSupport>> queryPageList(CoinSupport coinSupport,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinSupport> queryWrapper = QueryGenerator.initQueryWrapper(coinSupport, req.getParameterMap());
		Page<CoinSupport> page = new Page<CoinSupport>(pageNo, pageSize);
		IPage<CoinSupport> pageList = coinSupportService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinSupport
	 * @return
	 */
	@AutoLog(value = "支持的两户货币-添加")
	@ApiOperation(value="支持的两户货币-添加", notes="支持的两户货币-添加")
	@RequiresPermissions("qe:coin_support:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinSupport coinSupport) {
		coinSupportService.save(coinSupport);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinSupport
	 * @return
	 */
	@AutoLog(value = "支持的两户货币-编辑")
	@ApiOperation(value="支持的两户货币-编辑", notes="支持的两户货币-编辑")
	@RequiresPermissions("qe:coin_support:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinSupport coinSupport) {
		coinSupportService.updateById(coinSupport);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "支持的两户货币-通过id删除")
	@ApiOperation(value="支持的两户货币-通过id删除", notes="支持的两户货币-通过id删除")
	@RequiresPermissions("qe:coin_support:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinSupportService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "支持的两户货币-批量删除")
	@ApiOperation(value="支持的两户货币-批量删除", notes="支持的两户货币-批量删除")
	@RequiresPermissions("qe:coin_support:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinSupportService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "支持的两户货币-通过id查询")
	@ApiOperation(value="支持的两户货币-通过id查询", notes="支持的两户货币-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinSupport> queryById(@RequestParam(name="id",required=true) String id) {
		CoinSupport coinSupport = coinSupportService.getById(id);
		if(coinSupport==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinSupport);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinSupport
    */
    @RequiresPermissions("qe:coin_support:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinSupport coinSupport) {
        return super.exportXls(request, coinSupport, CoinSupport.class, "支持的两户货币");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_support:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinSupport.class);
    }

}
