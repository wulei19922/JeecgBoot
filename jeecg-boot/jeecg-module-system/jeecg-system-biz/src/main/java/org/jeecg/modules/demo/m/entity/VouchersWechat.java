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
 * @Description: 微信优惠券规则
 * @Author: jeecg-boot
 * @Date:   2025-03-13
 * @Version: V1.0
 */
@Data
@TableName("vouchers_wechat")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="vouchers_wechat对象", description="微信优惠券规则")
public class VouchersWechat implements Serializable {
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
	/**代金券名称*/
	@Excel(name = "代金券名称", width = 15)
    @ApiModelProperty(value = "代金券名称")
    private java.lang.String stockName;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String comment;
	/**所属商户*/
	@Excel(name = "所属商户", width = 15)
    @ApiModelProperty(value = "所属商户")
    private java.lang.String belongMerchant;
	/**开始时间*/
	@Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date availableBeginTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date availableEndTime;
	/**规则*/
	@Excel(name = "规则", width = 15)
    @ApiModelProperty(value = "规则")
    private java.lang.String stockUseRule;
	/**最大发券数*/
	@Excel(name = "最大发券数", width = 15)
    @ApiModelProperty(value = "最大发券数")
    private java.lang.String maxCoupons;
	/**总预算*/
	@Excel(name = "总预算", width = 15)
    @ApiModelProperty(value = "总预算")
    private java.lang.String maxAmount;
	/**单天预算发放上限单位：分*/
	@Excel(name = "单天预算发放上限单位：分", width = 15)
    @ApiModelProperty(value = "单天预算发放上限单位：分")
    private java.lang.String maxAmountByDay;
	/**单个用户可领个数*/
	@Excel(name = "单个用户可领个数", width = 15)
    @ApiModelProperty(value = "单个用户可领个数")
    private java.lang.String maxCouponsPerUser;
	/**是否开启自然人限制*/
    @Excel(name = "是否开启自然人限制", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "是否开启自然人限制")
    private java.lang.String naturalPersonLimit;
	/**是否开启防刷拦截*/
    @Excel(name = "是否开启防刷拦截", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "是否开启防刷拦截")
    private java.lang.String preventApiAbuse;
	/**代金券详情页*/
	@Excel(name = "代金券详情页", width = 15)
    @ApiModelProperty(value = "代金券详情页")
    private java.lang.String patternInfo;
	/**详细的活动规则*/
	@Excel(name = "详细的活动规则", width = 15)
    @ApiModelProperty(value = "详细的活动规则")
    private java.lang.String description;
	/**商户logo*/
	@Excel(name = "商户logo", width = 15)
    @ApiModelProperty(value = "商户logo")
    private java.lang.String merchantLogo;
	/**品牌名称*/
	@Excel(name = "品牌名称", width = 15)
    @ApiModelProperty(value = "品牌名称")
    private java.lang.String merchantName;
	/**背景颜色*/
	@Excel(name = "背景颜色", width = 15, dicCode = "COLOR")
	@Dict(dicCode = "COLOR")
    @ApiModelProperty(value = "背景颜色")
    private java.lang.String backgroundColor;
	/**券详情图片*/
	@Excel(name = "券详情图片", width = 15)
    @ApiModelProperty(value = "券详情图片")
    private java.lang.String couponImage;
	/**卡包跳转目标*/
	@Excel(name = "卡包跳转目标", width = 15)
    @ApiModelProperty(value = "卡包跳转目标")
    private java.lang.String jumpTarget;
	/**小程序appid*/
	@Excel(name = "小程序appid", width = 15)
    @ApiModelProperty(value = "小程序appid")
    private java.lang.String miniProgramAppid;
	/**小程序path*/
	@Excel(name = "小程序path", width = 15)
    @ApiModelProperty(value = "小程序path")
    private java.lang.String miniProgramPath;
	/**核销规则*/
	@Excel(name = "核销规则", width = 15)
    @ApiModelProperty(value = "核销规则")
    private java.lang.String couponUseRule;
	/**券生效时间*/
	@Excel(name = "券生效时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "券生效时间")
    private java.util.Date couponAvailableTime;
	/**固定时间段可用*/
	@Excel(name = "固定时间段可用", width = 15)
    @ApiModelProperty(value = "固定时间段可用")
    private java.lang.String fixAvailableTime;
	/**固定面额满减券使用规则*/
	@Excel(name = "固定面额满减券使用规则", width = 15)
    @ApiModelProperty(value = "固定面额满减券使用规则")
    private java.lang.String fixedNormalCoupon;
	/**面额*/
	@Excel(name = "面额", width = 15)
    @ApiModelProperty(value = "面额")
    private java.lang.Integer couponAmount;
	/**门槛*/
	@Excel(name = "门槛", width = 15)
    @ApiModelProperty(value = "门槛")
    private java.lang.Integer transactionMinimum;
	/**订单优惠标记*/
	@Excel(name = "订单优惠标记", width = 15)
    @ApiModelProperty(value = "订单优惠标记")
    private java.lang.String goodsTag;
	/**指定支付模式*/
	@Excel(name = "指定支付模式", width = 15)
    @ApiModelProperty(value = "指定支付模式")
    private java.lang.String tradeType;
	/**是否可叠加其他优惠*/
    @Excel(name = "是否可叠加其他优惠", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "是否可叠加其他优惠")
    private java.lang.String combineUse;
	/**可核销商品编码*/
	@Excel(name = "可核销商品编码", width = 15)
    @ApiModelProperty(value = "可核销商品编码")
    private java.lang.String availableItems;
	/**不参与优惠商品编码*/
	@Excel(name = "不参与优惠商品编码", width = 15)
    @ApiModelProperty(value = "不参与优惠商品编码")
    private java.lang.String unavailableItems;
	/**可核销商户号*/
	@Excel(name = "可核销商户号", width = 15)
    @ApiModelProperty(value = "可核销商户号")
    private java.lang.String availableMerchants;
	/**指定银行卡BIN*/
	@Excel(name = "指定银行卡BIN", width = 15)
    @ApiModelProperty(value = "指定银行卡BIN")
    private java.lang.String limitCard;
	/**银行卡名字*/
	@Excel(name = "银行卡名字", width = 15)
    @ApiModelProperty(value = "银行卡名字")
    private java.lang.String limitCardName;
	/**银行卡BIN*/
	@Excel(name = "银行卡BIN", width = 15)
    @ApiModelProperty(value = "银行卡BIN")
    private java.lang.String limitCardBin;
	/**指定付款方式*/
	@Excel(name = "指定付款方式", width = 15)
    @ApiModelProperty(value = "指定付款方式")
    private java.lang.String limitPay;
	/**营销经费*/
	@Excel(name = "营销经费", width = 15, dicCode = "no_cash")
	@Dict(dicCode = "no_cash")
    @ApiModelProperty(value = "营销经费")
    private java.lang.String noCash;
	/**批次类型*/
	@Excel(name = "批次类型", width = 15, dicCode = "stock_type")
	@Dict(dicCode = "stock_type")
    @ApiModelProperty(value = "批次类型")
    private java.lang.String stockType;
	/**商户单据号*/
	@Excel(name = "商户单据号", width = 15)
    @ApiModelProperty(value = "商户单据号")
    private java.lang.String outRequestNo;
	/**扩展属性*/
	@Excel(name = "扩展属性", width = 15)
    @ApiModelProperty(value = "扩展属性")
    private java.lang.String extInfo;
	/**所属表头*/
	@Excel(name = "所属表头", width = 15)
    @ApiModelProperty(value = "所属表头")
    private java.lang.String headId;
}
