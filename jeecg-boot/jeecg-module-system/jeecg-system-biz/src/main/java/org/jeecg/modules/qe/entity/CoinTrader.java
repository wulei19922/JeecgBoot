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
 * @Description: 平台账户支付记录
 * @Author: jeecg-boot
 * @Date:   2025-03-08
 * @Version: V1.0
 */
@Data
@TableName("coin_trader")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_trader对象", description="平台账户支付记录")
public class CoinTrader implements Serializable {
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
	/**支付货币*/
	@Excel(name = "支付货币", width = 15)
    @ApiModelProperty(value = "支付货币")
    private String symbol;
	/**支付金额*/
	@Excel(name = "支付金额", width = 15)
    @ApiModelProperty(value = "支付金额")
    private String paymount;
	/**支付交易所*/
	@Excel(name = "支付交易所", width = 15, dicCode = "exchange")
	@Dict(dicCode = "exchange")
    @ApiModelProperty(value = "支付交易所")
    private String exchange;
	/**支付状态*/
	@Excel(name = "支付状态", width = 15, dicCode = "paystatus")
	@Dict(dicCode = "paystatus")
    @ApiModelProperty(value = "支付状态")
    private String status;
	/**支出账户*/
	@Excel(name = "支出账户", width = 15, dictTable = "coin_keys", dicText = "key_name", dicCode = "id")
	@Dict(dictTable = "coin_keys", dicText = "key_name", dicCode = "id")
    @ApiModelProperty(value = "支出账户")
    private String fromAccount;
	/**接受账户*/
	@Excel(name = "接受账户", width = 15, dictTable = "coin_keys", dicText = "key_name", dicCode = "id")
	@Dict(dictTable = "coin_keys", dicText = "key_name", dicCode = "id")
    @ApiModelProperty(value = "接受账户")
    private String toAccount;
	/**支付备注*/
	@Excel(name = "支付备注", width = 15)
    @ApiModelProperty(value = "支付备注")
    private String descr;
}
