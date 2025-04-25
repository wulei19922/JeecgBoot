package org.jeecg.modules.qe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 机器人合约持仓表
 * @Author: jeecg-boot
 * @Date:   2025-04-25
 * @Version: V1.0
 */
@Data
@TableName("coin_bot_futures_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_bot_futures_order对象", description="机器人合约持仓表")
public class CoinBotFuturesOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**交易对*/
	@Excel(name = "交易对", width = 15)
    @ApiModelProperty(value = "交易对")
    private String symbol;
	/**订单*/
	@Excel(name = "订单", width = 15)
    @ApiModelProperty(value = "订单")
    private String orderId;
	/**合约倍数*/
	@Excel(name = "合约倍数", width = 15)
    @ApiModelProperty(value = "合约倍数")
    private Integer lever;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private Double num;
	/**平仓盈亏*/
	@Excel(name = "平仓盈亏", width = 15)
    @ApiModelProperty(value = "平仓盈亏")
    private Double profit;
	/**开仓价格*/
	@Excel(name = "开仓价格", width = 15)
    @ApiModelProperty(value = "开仓价格")
    private Double openPrice;
	/**平仓价格*/
	@Excel(name = "平仓价格", width = 15)
    @ApiModelProperty(value = "平仓价格")
    private Double closePrice;
	/**最大持仓*/
	@Excel(name = "最大持仓", width = 15)
    @ApiModelProperty(value = "最大持仓")
    private Double position;
	/**已平仓数量*/
	@Excel(name = "已平仓数量", width = 15)
    @ApiModelProperty(value = "已平仓数量")
    private Double closePostion;
	/**开仓时间*/
	@Excel(name = "开仓时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开仓时间")
    private Date openTime;
	/**最后平仓时间*/
	@Excel(name = "最后平仓时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后平仓时间")
    private Date closeTime;
	/**所属机器人*/
	@Excel(name = "所属机器人", width = 15, dictTable = "coin_bot", dicText = "instance_name", dicCode = "id")
	@Dict(dictTable = "coin_bot", dicText = "instance_name", dicCode = "id")
    @ApiModelProperty(value = "所属机器人")
    private String botId;
	/**订单*/
	@Excel(name = "订单", width = 15, dicCode = "BINANCE_ORDER_STATUS")
	@Dict(dicCode = "BINANCE_ORDER_STATUS")
    @ApiModelProperty(value = "订单")
    private String orderStatus;
	/**持仓方向*/
	@Excel(name = "持仓方向", width = 15, dicCode = "future_side")
	@Dict(dicCode = "future_side")
    @ApiModelProperty(value = "持仓方向")
    private String positionType;
}
