package org.jeecg.modules.demo.m.vo;

import java.util.List;
import org.jeecg.modules.demo.m.entity.MarketVouchers;
import org.jeecg.modules.demo.m.entity.MarketVouchersMerchants;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 支付营销代金券场景
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="market_vouchersPage对象", description="支付营销代金券场景")
public class MarketVouchersPage {

	/**主键*/
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
	/**批次名称*/
	@Excel(name = "批次名称", width = 15)
	@ApiModelProperty(value = "批次名称")
    private java.lang.String name;
	/**批次备注*/
	@Excel(name = "批次备注", width = 15)
	@ApiModelProperty(value = "批次备注")
    private java.lang.String remark;
	/**活动平台*/
	@Excel(name = "活动平台", width = 15, dicCode = "merchant_platfrom")
    @Dict(dicCode = "merchant_platfrom")
	@ApiModelProperty(value = "活动平台")
    private java.lang.String platform;
	/**开始时间*/
	@Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "开始时间")
    private java.util.Date startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "结束时间")
    private java.util.Date endTime;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "vouchers_status")
    @Dict(dicCode = "vouchers_status")
	@ApiModelProperty(value = "状态")
    private java.lang.String status;
	/**平台配置参数*/
	@Excel(name = "平台配置参数", width = 15)
	@ApiModelProperty(value = "平台配置参数")
    private java.lang.String params;
	/**规则配置ID*/
	@Excel(name = "规则配置ID", width = 15)
	@ApiModelProperty(value = "规则配置ID")
    private java.lang.String settingId;
	/**规则配置表*/
	@Excel(name = "规则配置表", width = 15)
	@ApiModelProperty(value = "规则配置表")
    private java.lang.String settingTable;

	@ExcelCollection(name="代金券场景归属商户")
	@ApiModelProperty(value = "代金券场景归属商户")
	private List<MarketVouchersMerchants> marketVouchersMerchantsList;

}
