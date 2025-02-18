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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 支持的两户货币
 * @Author: jeecg-boot
 * @Date:   2025-02-16
 * @Version: V1.0
 */
@Data
@TableName("coin_support")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_support对象", description="支持的两户货币")
public class CoinSupport implements Serializable {
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
	/**交易对*/
	@Excel(name = "交易对", width = 15)
    @ApiModelProperty(value = "交易对")
    private String symbol;
	/**平台*/
	@Excel(name = "平台", width = 15)
    @ApiModelProperty(value = "平台")
    private String platform;
	/**货币图标*/
	@Excel(name = "货币图标", width = 15)
    @ApiModelProperty(value = "货币图标")
    private String icourl;
}
