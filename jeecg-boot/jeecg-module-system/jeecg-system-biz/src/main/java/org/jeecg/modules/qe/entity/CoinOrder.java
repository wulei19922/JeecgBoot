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
 * @Description: 量化机器人订单
 * @Author: jeecg-boot
 * @Date:   2025-03-06
 * @Version: V1.0
 */
@Data
@TableName("coin_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_order对象", description="量化机器人订单")
public class CoinOrder implements Serializable {
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
	/**方向*/
	@Excel(name = "方向", width = 15)
    @ApiModelProperty(value = "方向")
    private String silder;
	/**	订单类型*/
	@Excel(name = "	订单类型", width = 15)
    @ApiModelProperty(value = "	订单类型")
    private String orderType;
	/**	成交均价*/
	@Excel(name = "	成交均价", width = 15)
    @ApiModelProperty(value = "	成交均价")
    private Double avgPrice;
	/**成交数量*/
	@Excel(name = "成交数量", width = 15)
    @ApiModelProperty(value = "成交数量")
    private Double num;
	/**成交价格*/
	@Excel(name = "成交价格", width = 15)
    @ApiModelProperty(value = "成交价格")
    private Double price;
	/**机器人ID*/
	@Excel(name = "机器人ID", width = 15, dictTable = "coin_bot", dicText = "instance_name", dicCode = "instance_name")
	@Dict(dictTable = "coin_bot", dicText = "instance_name", dicCode = "instance_name")
    @ApiModelProperty(value = "机器人ID")
    private String botId;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "BINANCE_ORDER_STATUS")
	@Dict(dicCode = "BINANCE_ORDER_STATUS")
    @ApiModelProperty(value = "状态")
    private String status;
	/**币安订单ID*/
	@Excel(name = "币安订单ID", width = 15)
    @ApiModelProperty(value = "币安订单ID")
    private String orderId;
	/**交易对*/
	@Excel(name = "交易对", width = 15)
    @ApiModelProperty(value = "交易对")
    private String symbol;
	/**匹配对*/
	@Excel(name = "匹配对", width = 15)
    @ApiModelProperty(value = "匹配对")
    private String matchId;
}
