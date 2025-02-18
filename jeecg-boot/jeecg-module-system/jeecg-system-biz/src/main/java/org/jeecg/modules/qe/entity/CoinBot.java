package org.jeecg.modules.qe.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 机器人列表
 * @Author: jeecg-boot
 * @Date:   2025-02-18
 * @Version: V1.0
 */
@Data
@TableName("coin_bot")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_bot对象", description="机器人列表")
public class CoinBot implements Serializable {
    private static final long serialVersionUID = 1L;

	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private String createBy;
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;
	/**当前状态*/
	@Excel(name = "当前状态", width = 15, dicCode = "bot_status")
	@Dict(dicCode = "bot_status")
    @ApiModelProperty(value = "当前状态")
    private String status;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private String type;
	/**开启状态*/
	@Excel(name = "开启状态", width = 15, dicCode = "qot_status")
	@Dict(dicCode = "qot_status")
    @ApiModelProperty(value = "开启状态")
    private Integer openStatus;
	/**用户*/
	@Excel(name = "用户", width = 15, dictTable = "li_member", dicText = "username", dicCode = "id")
	@Dict(dictTable = "li_member", dicText = "username", dicCode = "id")
    @ApiModelProperty(value = "用户")
    private String memberId;
	/**交易对*/
	@Excel(name = "交易对", width = 15, dictTable = "coin_support", dicText = "symbol", dicCode = "symbol")
	@Dict(dictTable = "coin_support", dicText = "symbol", dicCode = "symbol")
    @ApiModelProperty(value = "交易对")
    private String symbol;
	/**总投资额*/
	@Excel(name = "总投资额", width = 15)
    @ApiModelProperty(value = "总投资额")
    private Double totalInvest;
	/**收益*/
	@Excel(name = "收益", width = 15)
    @ApiModelProperty(value = "收益")
    private Double income;
	/**网格收益*/
	@Excel(name = "网格收益", width = 15)
    @ApiModelProperty(value = "网格收益")
    private Double incomeGride;
	/**净利润*/
	@Excel(name = "净利润", width = 15)
    @ApiModelProperty(value = "净利润")
    private Double profit;
	/**配对次数*/
	@Excel(name = "配对次数", width = 15)
    @ApiModelProperty(value = "配对次数")
    private Integer matchNum;
	/**区间最大价格*/
	@Excel(name = "区间最大价格", width = 15)
    @ApiModelProperty(value = "区间最大价格")
    private Double maxPrice;
	/**区间最小价格*/
	@Excel(name = "区间最小价格", width = 15)
    @ApiModelProperty(value = "区间最小价格")
    private Double minPrice;
	/**网格数量*/
	@Excel(name = "网格数量", width = 15)
    @ApiModelProperty(value = "网格数量")
    private Integer grideNum;
	/**单次交易数量*/
	@Excel(name = "单次交易数量", width = 15)
    @ApiModelProperty(value = "单次交易数量")
    private Integer perOrder;
	/**机器人节点机器*/
	@Excel(name = "机器人节点机器", width = 15)
    @ApiModelProperty(value = "机器人节点机器")
    private String nodeName;
	/**节点IP*/
	@Excel(name = "节点IP", width = 15)
    @ApiModelProperty(value = "节点IP")
    private String nodeIp;
	/**实例名*/
	@Excel(name = "实例名", width = 15)
    @ApiModelProperty(value = "实例名")
    private String instanceName;
	/**单网格利润率*/
	@Excel(name = "单网格利润率", width = 15)
    @ApiModelProperty(value = "单网格利润率")
    private Double grideProfit;
	/**环境*/
	@Excel(name = "环境", width = 15)
    @ApiModelProperty(value = "环境")
    private String env;
	/**持仓*/
	@Excel(name = "持仓", width = 15)
    @ApiModelProperty(value = "持仓")
    private Double positions;
}
