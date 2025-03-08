package org.jeecg.modules.qe.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.qe.entity.CoinHelp;
import org.jeecg.modules.qe.service.ICoinHelpService;

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
 * @Description: 帮助中心
 * @Author: jeecg-boot
 * @Date:   2025-03-08
 * @Version: V1.0
 */
@Api(tags="帮助中心")
@RestController
@RequestMapping("/qe/coinHelp")
@Slf4j
public class CoinHelpController extends JeecgController<CoinHelp, ICoinHelpService> {
	@Autowired
	private ICoinHelpService coinHelpService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinHelp
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "帮助中心-分页列表查询")
	@ApiOperation(value="帮助中心-分页列表查询", notes="帮助中心-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinHelp>> queryPageList(CoinHelp coinHelp,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinHelp> queryWrapper = QueryGenerator.initQueryWrapper(coinHelp, req.getParameterMap());
		Page<CoinHelp> page = new Page<CoinHelp>(pageNo, pageSize);
		IPage<CoinHelp> pageList = coinHelpService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinHelp
	 * @return
	 */
	@AutoLog(value = "帮助中心-添加")
	@ApiOperation(value="帮助中心-添加", notes="帮助中心-添加")
	@RequiresPermissions("qe:coin_help:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinHelp coinHelp) {
		coinHelpService.save(coinHelp);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinHelp
	 * @return
	 */
	@AutoLog(value = "帮助中心-编辑")
	@ApiOperation(value="帮助中心-编辑", notes="帮助中心-编辑")
	@RequiresPermissions("qe:coin_help:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinHelp coinHelp) {
		coinHelpService.updateById(coinHelp);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "帮助中心-通过id删除")
	@ApiOperation(value="帮助中心-通过id删除", notes="帮助中心-通过id删除")
	@RequiresPermissions("qe:coin_help:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinHelpService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "帮助中心-批量删除")
	@ApiOperation(value="帮助中心-批量删除", notes="帮助中心-批量删除")
	@RequiresPermissions("qe:coin_help:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinHelpService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "帮助中心-通过id查询")
	@ApiOperation(value="帮助中心-通过id查询", notes="帮助中心-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinHelp> queryById(@RequestParam(name="id",required=true) String id) {
		CoinHelp coinHelp = coinHelpService.getById(id);
		if(coinHelp==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinHelp);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinHelp
    */
    @RequiresPermissions("qe:coin_help:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinHelp coinHelp) {
        return super.exportXls(request, coinHelp, CoinHelp.class, "帮助中心");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_help:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinHelp.class);
    }

}
