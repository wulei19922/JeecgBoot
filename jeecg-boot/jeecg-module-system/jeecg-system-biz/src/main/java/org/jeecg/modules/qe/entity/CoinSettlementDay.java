package org.jeecg.modules.qe.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 每日结算
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
@Data
@TableName("coin_settlement_day")
@ApiModel(value="coin_settlement_day对象", description="每日结算")
public class CoinSettlementDay implements Serializable {
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
	/**结算日期*/
    @Excel(name = "结算日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结算日期")
    private Date day;
	/**运行中的机器人*/
    @Excel(name = "运行中的机器人", width = 15)
    @ApiModelProperty(value = "运行中的机器人")
    private Integer botRuning;
	/**用户机器人日利润*/
    @Excel(name = "用户机器人日利润", width = 15)
    @ApiModelProperty(value = "用户机器人日利润")
    private Double profitDay;
	/**用户机器人日亏损*/
    @Excel(name = "用户机器人日亏损", width = 15)
    @ApiModelProperty(value = "用户机器人日亏损")
    private Double lossDay;
	/**公司机器人净利润*/
    @Excel(name = "公司机器人净利润", width = 15)
    @ApiModelProperty(value = "公司机器人净利润")
    private Double profitDayNetpfofit;
	/**市场待结利润*/
    @Excel(name = "市场待结利润", width = 15)
    @ApiModelProperty(value = "市场待结利润")
    private String settlementDayIng;
	/**市场已结算利润*/
    @Excel(name = "市场已结算利润", width = 15)
    @ApiModelProperty(value = "市场已结算利润")
    private String settlementDayEd;
	/**结算状态*/
    @Excel(name = "结算状态", width = 15, dicCode = "settlement")
    @Dict(dicCode = "settlement")
    @ApiModelProperty(value = "结算状态")
    private String status;
}
