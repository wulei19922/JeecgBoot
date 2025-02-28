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
 * @Description: 量化钱包
 * @Author: jeecg-boot
 * @Date:   2025-02-28
 * @Version: V1.0
 */
@Data
@TableName("coin_wallet")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_wallet对象", description="量化钱包")
public class CoinWallet implements Serializable {
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
	/**余额*/
	@Excel(name = "余额", width = 15)
    @ApiModelProperty(value = "余额")
    private Double free;
	/**锁定*/
	@Excel(name = "锁定", width = 15)
    @ApiModelProperty(value = "锁定")
    private Double locked;
	/**钱包类型*/
	@Excel(name = "钱包类型", width = 15)
    @ApiModelProperty(value = "钱包类型")
    private String symbol;
	/**所属用户*/
	@Excel(name = "所属用户", width = 15, dictTable = "sys_user", dicText = "username", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "username", dicCode = "id")
    @ApiModelProperty(value = "所属用户")
    private String memberId;
	/**密钥*/
	@Excel(name = "密钥", width = 15, dictTable = "coin_keys", dicText = "key_name", dicCode = "id")
	@Dict(dictTable = "coin_keys", dicText = "key_name", dicCode = "id")
    @ApiModelProperty(value = "密钥")
    private String memberKey;
	/**所属平台*/
	@Excel(name = "所属平台", width = 15, dicCode = "exchange")
	@Dict(dicCode = "exchange")
    @ApiModelProperty(value = "所属平台")
    private String exchange;
	/**今日盈利*/
	@Excel(name = "今日盈利", width = 15)
    @ApiModelProperty(value = "今日盈利")
    private Double dayProfit;
}
