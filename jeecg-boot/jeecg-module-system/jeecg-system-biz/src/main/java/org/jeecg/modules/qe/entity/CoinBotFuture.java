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
 * @Description: 机器人合约表委托
 * @Author: jeecg-boot
 * @Date:   2025-04-25
 * @Version: V1.0
 */
@Data
@TableName("coin_bot_future")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_bot_future对象", description="机器人合约表委托")
public class CoinBotFuture implements Serializable {
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
	/**类型*/
	@Excel(name = "类型", width = 15, dicCode = "delegation_type")
	@Dict(dicCode = "delegation_type")
    @ApiModelProperty(value = "类型")
    private String type;
	/**方向*/
	@Excel(name = "方向", width = 15, dicCode = "future_side")
	@Dict(dicCode = "future_side")
    @ApiModelProperty(value = "方向")
    private String silde;
	/**平均价格*/
	@Excel(name = "平均价格", width = 15)
    @ApiModelProperty(value = "平均价格")
    private Double avgPrice;
	/**价格*/
	@Excel(name = "价格", width = 15)
    @ApiModelProperty(value = "价格")
    private Double price;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private Double num;
	/**只减仓*/
    @Excel(name = "只减仓", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "只减仓")
    private String postionDown;
	/**只做Maker*/
    @Excel(name = "只做Maker", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "只做Maker")
    private String isMaker;
	/**触发条件*/
	@Excel(name = "触发条件", width = 15)
    @ApiModelProperty(value = "触发条件")
    private String activeCondition;
	/**机器人*/
	@Excel(name = "机器人", width = 15)
    @ApiModelProperty(value = "机器人")
    private String botId;
	/**平台*/
	@Excel(name = "平台", width = 15, dicCode = "exchange")
	@Dict(dicCode = "exchange")
    @ApiModelProperty(value = "平台")
    private String exchange;
	/**订单*/
	@Excel(name = "订单", width = 15)
    @ApiModelProperty(value = "订单")
    private String orderId;
	/**订单状态*/
	@Excel(name = "订单状态", width = 15, dicCode = "BINANCE_ORDER_STATUS")
	@Dict(dicCode = "BINANCE_ORDER_STATUS")
    @ApiModelProperty(value = "订单状态")
    private String orderStatus;
	/**持仓方向*/
	@Excel(name = "持仓方向", width = 15, dicCode = "future_side")
	@Dict(dicCode = "future_side")
    @ApiModelProperty(value = "持仓方向")
    private String positionType;
}
