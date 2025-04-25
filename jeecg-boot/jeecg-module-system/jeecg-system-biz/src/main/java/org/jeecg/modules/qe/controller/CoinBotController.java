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

import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSONObject;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.qe.entity.CoinBot;
import org.jeecg.modules.qe.entity.CoinBotDTO;
import org.jeecg.modules.qe.entity.TickerResutl;
import org.jeecg.modules.qe.service.ICoinBotService;
import org.jeecg.modules.qe.service.impl.BinanceClientService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
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
 * @Description: 机器人列表
 * @Author: jeecg-boot
 * @Date:   2025-02-18
 * @Version: V1.0
 */
@Api(tags="机器人列表")
@RestController
@RequestMapping("/qe/coinBot")
@Slf4j
public class CoinBotController extends JeecgController<CoinBot, ICoinBotService> {
	@Autowired
	private ICoinBotService coinBotService;


	@Autowired
	BinanceClientService binanceClientService;
	/**
	 * 分页列表查询
	 *
	 * @param coinBot
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "机器人列表-分页列表查询")
	@ApiOperation(value="机器人列表-分页列表查询", notes="机器人列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CoinBot>> queryPageList(CoinBot coinBot,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("status", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("openStatus", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("memberId", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<CoinBot> queryWrapper = QueryGenerator.initQueryWrapper(coinBot, req.getParameterMap(),customeRuleMap);
//		queryWrapper.select(CoinBot.class, tableField -> !tableField.getColumn().equals("grid_config"));
		Page<CoinBot> page = new Page<CoinBot>(pageNo, pageSize);
		IPage<CoinBot> pageList = coinBotService.page(page, queryWrapper);

		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coinBot
	 * @return
	 */
	@AutoLog(value = "机器人列表-添加")
	@ApiOperation(value="机器人列表-添加", notes="机器人列表-添加")
	@RequiresPermissions("qe:coin_bot:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CoinBot coinBot) {
		boolean check = coinBotService.check(coinBot);
		if (check){
			coinBotService.save(coinBot);
			return Result.OK("添加成功！");

		}else{
			return Result.error("该用户 该交易对已经存在机器人，请勿重复添加");

		}
	}
	
	/**
	 *  编辑
	 *
	 * @param coinBot
	 * @return
	 */
	@AutoLog(value = "机器人列表-编辑")
	@ApiOperation(value="机器人列表-编辑", notes="机器人列表-编辑")
	@RequiresPermissions("qe:coin_bot:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CoinBot coinBot) {
		boolean check = coinBotService.check(coinBot);
		if (check){
			coinBotService.updateById(coinBot);
			return Result.OK("编辑成功!");

		}else {
			return Result.error("该用户 该交易对已经存在机器人，请勿重复添加");

		}
	}

	 @AutoLog(value = "机器人列表-编辑")
	 @ApiOperation(value="机器人列表-编辑", notes="机器人列表-编辑")
	 @RequiresPermissions("qe:coin_bot:edit")
		 @RequestMapping(value = "/editconfig", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> editConfig(@RequestBody JSONObject param) {

		 String id = param.getString("id");
		 String gridConfig = param.getString("gridConfig");
		 Float addInvest = param.getFloat("addInvest");
		 boolean u= coinBotService.editGrideConfig(id,gridConfig,addInvest);
		 if (u){
			 return Result.OK("编辑成功!");
		 }else {
			 return Result.error("修改失败");

		 }
	 }
	 @AutoLog(value = "机器人列表-任务管理")
	 @ApiOperation(value="机器人列表-任务管理", notes="机器人列表-任务管理")
	 @RequiresPermissions("qe:coin_bot:edit")
	 @RequestMapping(value = "/kafka/manager", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> sendDirectiveToKafka(@RequestBody JSONObject param) {

		 String[] ids = param.getString("ids").split(",");
		 String  status = param.getString("status");
		 boolean u= coinBotService.operateBatchToKafka(Arrays.asList(ids),status);
		 if (u){
			 return Result.OK("处理中");
		 }else {
			 return Result.error("处理失败");

		 }
	 }

	 @AutoLog(value = "机器人列表-机器人指令")
	 @ApiOperation(value="机器人列表-机器人指令", notes="机器人列表-机器人指令")
	 @RequiresPermissions("qe:coin_bot:edit")
	 @RequestMapping(value = "/kafka/pod", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> sendDirectiveToKafkaPod(@RequestBody JSONObject param) {

		 String[] ids = param.getString("ids").split(",");
		 String  status = param.getString("status");
		 boolean u= coinBotService.operateBatchToKafkaBot(Arrays.asList(ids),status);
		 if (u){
			 return Result.OK("处理中");
		 }else {
			 return Result.error("处理失败");

		 }
	 }

	 @AutoLog(value = "机器人列表-指令")
	 @ApiOperation(value="机器人列表-指令", notes="机器人列表-指令")
	 @RequiresPermissions("qe:coin_bot:edit")
	 @RequestMapping(value = "/directive", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> sendDirective(@RequestBody JSONObject param) {

		 String id = param.getString("id");
		 String gridConfig = param.getString("gridConfig");
		 Float addInvest = param.getFloat("addInvest");
		 boolean u= coinBotService.editGrideConfig(id,gridConfig,addInvest);
		 if (u){
			 return Result.OK("编辑成功!");
		 }else {
			 return Result.error("修改失败");

		 }
	 }



	 /**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "机器人列表-通过id删除")
	@ApiOperation(value="机器人列表-通过id删除", notes="机器人列表-通过id删除")
	@RequiresPermissions("qe:coin_bot:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		coinBotService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "机器人列表-批量删除")
	@ApiOperation(value="机器人列表-批量删除", notes="机器人列表-批量删除")
	@RequiresPermissions("qe:coin_bot:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coinBotService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	 /**
	  *  批量操作
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "机器人列表-批量操作")
	 @ApiOperation(value="机器人列表-批量操作", notes="机器人列表-批量操作")
	 @DeleteMapping(value = "/operateBatch")
	 public Result<String> operateBatch(@RequestParam(name="ids",required=true) String ids,@RequestParam(name="type",required=true) String type) {
		 this.coinBotService.operateBatch(Arrays.asList(ids.split(",")),type);

		 return Result.OK("批量删除成功!");
	 }

	 @AutoLog(value = "机器人列表-批量操作")
	 @ApiOperation(value="机器人列表-批量操作", notes="机器人列表-批量操作")
	 @PostMapping(value = "/operateBatch")
	 public Result<String> stopAllBot(@RequestBody JSONObject jsonObject) {
		 String ids=jsonObject.getString("ids");
		 String type=jsonObject.getString("type");
		 this.coinBotService.operateBatch(Arrays.asList(ids.split(",")),type);

		 return Result.OK("批量删除成功!");
	 }


	 /**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "机器人列表-通过id查询")
	@ApiOperation(value="机器人列表-通过id查询", notes="机器人列表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CoinBot> queryById(@RequestParam(name="id",required=true) String id) {
		CoinBot coinBot = coinBotService.getById(id);
		if(coinBot==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coinBot);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coinBot
    */
    @RequiresPermissions("qe:coin_bot:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoinBot coinBot) {
        return super.exportXls(request, coinBot, CoinBot.class, "机器人列表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qe:coin_bot:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CoinBot.class);
    }

}
