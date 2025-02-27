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
 * @Description: 资金明细变化
 * @Author: jeecg-boot
 * @Date:   2025-02-26
 * @Version: V1.0
 */
@Data
@TableName("coin_funds_change")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_funds_change对象", description="资金明细变化")
public class CoinFundsChange implements Serializable {
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
	/**资金类型*/
	@Excel(name = "资金类型", width = 15, dicCode = "funds")
	@Dict(dicCode = "funds")
    @ApiModelProperty(value = "资金类型")
    private String fundsType;
	/**变动类型*/
	@Excel(name = "变动类型", width = 15, dicCode = "fundschange")
	@Dict(dicCode = "fundschange")
    @ApiModelProperty(value = "变动类型")
    private String changeType;
	/**变动数量*/
	@Excel(name = "变动数量", width = 15)
    @ApiModelProperty(value = "变动数量")
    private Double num;
	/**变动后*/
	@Excel(name = "变动后", width = 15)
    @ApiModelProperty(value = "变动后")
    private Double numAfter;
	/**变动前*/
	@Excel(name = "变动前", width = 15)
    @ApiModelProperty(value = "变动前")
    private Double numBefore;
	/**所属用户*/
	@Excel(name = "所属用户", width = 15, dictTable = "sys_user", dicText = "username", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "username", dicCode = "id")
    @ApiModelProperty(value = "所属用户")
    private String memberId;
	/**变动内容说明*/
	@Excel(name = "变动内容说明", width = 15)
    @ApiModelProperty(value = "变动内容说明")
    private String mark;
}
