package org.jeecg.modules.demo.m.entity;

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
 * @Description: 银行营销表
 * @Author: jeecg-boot
 * @Date:   2025-04-09
 * @Version: V1.0
 */
@Data
@TableName("voucher_wechat_bank")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="voucher_wechat_bank对象", description="银行营销表")
public class VoucherWechatBank implements Serializable {
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
	/**投放渠道*/
	@Excel(name = "投放渠道", width = 15, dicCode = "wechat_sent_channel")
	@Dict(dicCode = "wechat_sent_channel")
    @ApiModelProperty(value = "投放渠道")
    private java.lang.String sendChannel;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String actName;
	/**活动银行*/
	@Excel(name = "活动银行", width = 15, dictTable = "wechat_bank", dicText = "bank_name", dicCode = "bank_value")
	@Dict(dictTable = "wechat_bank", dicText = "bank_name", dicCode = "bank_value")
    @ApiModelProperty(value = "活动银行")
    private java.lang.String bankName;
	/**银行logo*/
	@Excel(name = "银行logo", width = 15, dictTable = "wechat_bank", dicText = "bank_name", dicCode = "bank_logo")
	@Dict(dictTable = "wechat_bank", dicText = "bank_name", dicCode = "bank_logo")
    @ApiModelProperty(value = "银行logo")
    private java.lang.String bankLogo;
	/**消费门槛*/
	@Excel(name = "消费门槛", width = 15)
    @ApiModelProperty(value = "消费门槛")
    private java.lang.Integer stockMin;
	/**免减金额*/
	@Excel(name = "免减金额", width = 15)
    @ApiModelProperty(value = "免减金额")
    private java.lang.Integer stockVal;
	/**活动预算*/
	@Excel(name = "活动预算", width = 15)
    @ApiModelProperty(value = "活动预算")
    private java.lang.Integer stockBudget;
	/**开始时间*/
	@Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date beginTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date endTime;
	/**领券后几天可以使用*/
	@Excel(name = "领券后几天可以使用", width = 15)
    @ApiModelProperty(value = "领券后几天可以使用")
    private java.lang.Integer relativeDays;
	/**使用规则*/
	@Excel(name = "使用规则", width = 15)
    @ApiModelProperty(value = "使用规则")
    private java.lang.String actRuleIntro;
	/**单人领取上限*/
	@Excel(name = "单人领取上限", width = 15)
    @ApiModelProperty(value = "单人领取上限")
    private java.lang.Integer userMaxQuota;
	/**单日发放上限*/
	@Excel(name = "单日发放上限", width = 15)
    @ApiModelProperty(value = "单日发放上限")
    private java.lang.Integer dailyBudget;
	/**发放方*/
	@Excel(name = "发放方", width = 15)
    @ApiModelProperty(value = "发放方")
    private java.lang.String senderMchids;
	/** 自然人防刷*/
    @Excel(name = " 自然人防刷", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = " 自然人防刷")
    private java.lang.String useNaturalDefense;
	/**小号拦截*/
    @Excel(name = "小号拦截", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "小号拦截")
    private java.lang.String useSpamBlock;
	/**背景颜色*/
	@Excel(name = "背景颜色", width = 15)
    @ApiModelProperty(value = "背景颜色")
    private java.lang.String bgColor;
	/**car_bin*/
	@Excel(name = "car_bin", width = 15)
    @ApiModelProperty(value = "car_bin")
    private java.lang.String cardBinInfo;
	/**叠加使用*/
    @Excel(name = "叠加使用", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "叠加使用")
    private java.lang.String combineUse;
	/**固定值*/
	@Excel(name = "固定值", width = 15)
    @ApiModelProperty(value = "固定值")
    private java.lang.Integer fixedValue;
	/**配额所属商户*/
	@Excel(name = "配额所属商户", width = 15)
    @ApiModelProperty(value = "配额所属商户")
    private java.lang.String quotaBelongMchId;
	/**配额所属银行*/
	@Excel(name = "配额所属银行", width = 15)
    @ApiModelProperty(value = "配额所属银行")
    private java.lang.String quotaBelongBankName;
	/**配额银行卡名称*/
	@Excel(name = "配额银行卡名称", width = 15)
    @ApiModelProperty(value = "配额银行卡名称")
    private java.lang.String quotaBelongBankCardTypeName;
	/**配额银行卡*/
	@Excel(name = "配额银行卡", width = 15)
    @ApiModelProperty(value = "配额银行卡")
    private java.lang.String quotaBelongBankCardType;
	/**配额银行所属产品*/
	@Excel(name = "配额银行所属产品", width = 15)
    @ApiModelProperty(value = "配额银行所属产品")
    private java.lang.String quotaBelongProduct;
	/**序列号*/
	@Excel(name = "序列号", width = 15)
    @ApiModelProperty(value = "序列号")
    private java.lang.String sequence;
	/**TOKEN*/
	@Excel(name = "TOKEN", width = 15)
    @ApiModelProperty(value = "TOKEN")
    private java.lang.String eccCsrfToken;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "wechat_bank_status")
	@Dict(dicCode = "wechat_bank_status")
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
}
