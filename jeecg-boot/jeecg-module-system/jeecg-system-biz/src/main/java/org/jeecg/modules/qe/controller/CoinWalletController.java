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

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.qe.entity.CoinWallet;
import org.jeecg.modules.qe.service.ICoinWalletService;

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
 * @Description: 量化钱包
 * @Author: jeecg-boot
 * @Date:   2025-02-27
 * @Version: V1.0
 */
@Api(tags="量化钱包")
@RestController
@RequestMapping("/qe/coinWallet")
@Slf4j
public class CoinWalletController extends JeecgController<CoinWallet, ICoinWalletService> {
	@Autowired
	private ICoinWalletService coinWalletService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coinWallet
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "量化钱包-分页列表查询")
	@ApiOperation(value="量化钱包-分页列表查询", notes="量化钱包-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinWallet>> queryPageList(CoinWallet coinWallet,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<CoinWallet> queryWrapper = QueryGenerator.initQueryWrapper(coinWallet, req.getParameterMap());
		Page<CoinWallet> page = new Page<CoinWallet>(pageNo, pageSize);
		IPage<CoinWallet> pageList = coinWalletService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinWallet
	 * @return
	 */
	@AutoLog(value = "量化钱包-添加")
	@ApiOperation(value="量化钱包-添加", notes="量化钱包-添加")
	@RequiresPermissions("qe:coin_wallet:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinWallet coinWallet) {
		coinWalletService.save(coinWallet);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coinWallet
	 * @return
	 */
	@AutoLog(value = "量化钱包-编辑")
	@ApiOperation(value="量化钱包-编辑", notes="量化钱包-编辑")
	@RequiresPermissions("qe:coin_wallet:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinWallet coinWallet) {
		coinWalletService.updateById(coinWallet);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "量化钱包-通过id删除")
	@ApiOperation(value="量化钱包-通过id删除", notes="量化钱包-通过id删除")
	@RequiresPermissions("qe:coin_wallet:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinWalletService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "量化钱包-批量删除")
	@ApiOperation(value="量化钱包-批量删除", notes="量化钱包-批量删除")
	@RequiresPermissions("qe:coin_wallet:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinWalletService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "量化钱包-通过id查询")
	@ApiOperation(value="量化钱包-通过id查询", notes="量化钱包-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinWallet> queryById(@RequestParam(name="id",required=true) String id) {
		CoinWallet coinWallet = coinWalletService.getById(id);
		if(coinWallet==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinWallet);
	}


	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 //@AutoLog(value = "量化钱包-通过id查询")
	 @ApiOperation(value="量化钱包-通过id查询", notes="量化钱包-通过id查询")
	 @GetMapping(value = "/my")
	 public Result<CoinWallet> queryMy() {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 QueryWrapper<CoinWallet>queryWrapper=new QueryWrapper<>();
		 queryWrapper.eq("member_id",sysUser.getId());

		 List<CoinWallet> list = coinWalletService.list(queryWrapper);

		 if(!list.isEmpty()) {
			 return Result.OK(list.get(0));
		 }
		 return Result.OK(null);
	 }


    /**
    * 导出excel
    *
    * @param request
    * @param coinWallet
    */
    @RequiresPermissions("qe:coin_wallet:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinWallet coinWallet) {
        return super.exportXls(request, coinWallet, CoinWallet.class, "量化钱包");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_wallet:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinWallet.class);
    }

}
