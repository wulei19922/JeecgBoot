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
 * @Description: 量化机器人订单
 * @Author: jeecg-boot
 * @Date:   2025-02-17
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
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**方向*/
	@Excel(name = "方向", width = 15)
    @ApiModelProperty(value = "方向")
    private java.lang.String silder;
	/**	订单类型*/
	@Excel(name = "	订单类型", width = 15)
    @ApiModelProperty(value = "	订单类型")
    private java.lang.String orderType;
	/**	成交均价*/
	@Excel(name = "	成交均价", width = 15)
    @ApiModelProperty(value = "	成交均价")
    private java.lang.Double avgPrice;
	/**成交数量*/
	@Excel(name = "成交数量", width = 15)
    @ApiModelProperty(value = "成交数量")
    private java.lang.Integer num;
	/**成交价格*/
	@Excel(name = "成交价格", width = 15)
    @ApiModelProperty(value = "成交价格")
    private java.lang.Double price;
	/**机器人ID*/
	@Excel(name = "机器人ID", width = 15)
    @ApiModelProperty(value = "机器人ID")
    private java.lang.String botId;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "BINANCE_ORDER_STATUS")
	@Dict(dicCode = "BINANCE_ORDER_STATUS")
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
}
