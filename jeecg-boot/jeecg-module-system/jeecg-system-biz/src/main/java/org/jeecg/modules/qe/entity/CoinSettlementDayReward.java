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
 * @Description: 结算给市场的利润明细
 * @Author: jeecg-boot
 * @Date:   2025-03-07
 * @Version: V1.0
 */
@Data
@TableName("coin_settlement_day_reward")
@ApiModel(value="coin_settlement_day_reward对象", description="结算给市场的利润明细")
public class CoinSettlementDayReward implements Serializable {
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
	/**分润用户*/
	@Excel(name = "分润用户", width = 15)
    @Dict(dicCode = "id",dicText = "username",dictTable = "sys_user")
    @ApiModelProperty(value = "分润用户")
    private String memberId;
	/**用户级别*/
	@Excel(name = "用户级别", width = 15)
    @Dict(dicCode = "id",dicText = "role_name",dictTable = "sys_role")
    @ApiModelProperty(value = "用户级别")
    private String memberGrade;
	/**明细说明*/
	@Excel(name = "明细说明", width = 15)
    @ApiModelProperty(value = "明细说明")
    private String detail;
	/**奖励数量*/
	@Excel(name = "奖励数量", width = 15)
    @ApiModelProperty(value = "奖励数量")
    private String reward;
	/**所属平台*/
	@Excel(name = "所属平台", width = 15)
    @Dict(dicCode = "exchange")
    @ApiModelProperty(value = "所属平台")
    private String exchange;
	/**表头*/
    @ApiModelProperty(value = "表头")
    private String headId;
	/**基础用户*/
	@Excel(name = "基础用户", width = 15)
    @Dict(dicCode = "id",dicText = "username",dictTable = "sys_user")
    @ApiModelProperty(value = "基础用户")
    private String inviteMemberId;
	/**结算状态*/
	@Excel(name = "结算状态", width = 15)
    @Dict(dicCode = "settlement")
    @ApiModelProperty(value = "结算状态")
    private String settlementStatus;
}
