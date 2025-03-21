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
 * @Description: 交易提交申请
 * @Author: jeecg-boot
 * @Date:   2025-03-21
 * @Version: V1.0
 */
@Data
@TableName("coin_trade_apply")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_trade_apply对象", description="交易提交申请")
public class CoinTradeApply implements Serializable {
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
	/**转出地址*/
	@Excel(name = "转出地址", width = 15)
    @ApiModelProperty(value = "转出地址")
    private String addressFrom;
	/**接收地址*/
	@Excel(name = "接收地址", width = 15)
    @ApiModelProperty(value = "接收地址")
    private String addressTo;
	/**金额*/
	@Excel(name = "金额", width = 15)
    @ApiModelProperty(value = "金额")
    private Double num;
	/**序列号*/
	@Excel(name = "序列号", width = 15)
    @ApiModelProperty(value = "序列号")
    private String serinal;
	/**申请类别*/
	@Excel(name = "申请类别", width = 15, dicCode = "trade_apply_type")
	@Dict(dicCode = "trade_apply_type")
    @ApiModelProperty(value = "申请类别")
    private String type;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15, dicCode = "audit_status")
	@Dict(dicCode = "audit_status")
    @ApiModelProperty(value = "审核状态")
    private String auditStatus;
	/**交易图片*/
	@Excel(name = "交易图片", width = 15)
    @ApiModelProperty(value = "交易图片")
    private String orderImg;
}
