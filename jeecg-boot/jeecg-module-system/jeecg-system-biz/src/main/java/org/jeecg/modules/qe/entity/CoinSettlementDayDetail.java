package org.jeecg.modules.qe.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 机器人点数利润明细
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
@Data
@TableName("coin_settlement_day_detail")
@ApiModel(value="coin_settlement_day_detail对象", description="机器人点数利润明细")
public class CoinSettlementDayDetail implements Serializable {
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
	/**表头ID*/
    @ApiModelProperty(value = "表头ID")
    private String headId;
	/**用户*/
	@Excel(name = "用户", width = 15)
    @Dict(dicCode = "id",dicText = "username",dictTable = "sys_user")
    @ApiModelProperty(value = "用户")
    private String memberId;
	/**交易对*/
	@Excel(name = "交易对", width = 15)
    @ApiModelProperty(value = "交易对")
    private String symbol;
	/**用户利润*/
	@Excel(name = "用户利润", width = 15)
    @ApiModelProperty(value = "用户利润")
    private Double profitDayUser;
	/**机器人费用*/
	@Excel(name = "机器人费用", width = 15)
    @ApiModelProperty(value = "机器人费用")
    private Double botProfit;
}
