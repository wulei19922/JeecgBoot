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
 * @Description: coin_version
 * @Author: jeecg-boot
 * @Date:   2025-02-24
 * @Version: V1.0
 */
@Data
@TableName("coin_version")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_version对象", description="coin_version")
public class CoinVersion implements Serializable {
    private static final long serialVersionUID = 1L;

	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private String createBy;
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;
	/**版本号*/
	@Excel(name = "版本号", width = 15)
    @ApiModelProperty(value = "版本号")
    private Integer versionCode;
	/**版本名*/
	@Excel(name = "版本名", width = 15)
    @ApiModelProperty(value = "版本名")
    private String versionName;
	/**版本信息*/
	@Excel(name = "版本信息", width = 15)
    @ApiModelProperty(value = "版本信息")
    private String versionInfo;
	/**更新类型*/
	@Excel(name = "更新类型", width = 15)
    @ApiModelProperty(value = "更新类型")
    private String updateType;
	/**下载地址*/
	@Excel(name = "下载地址", width = 15)
    @ApiModelProperty(value = "下载地址")
    private String downloadUrl;
	/**android下载地址*/
	@Excel(name = "android下载地址", width = 15)
    @ApiModelProperty(value = "android下载地址")
    private String androidUrl;
	/**ios下载地址*/
	@Excel(name = "ios下载地址", width = 15)
    @ApiModelProperty(value = "ios下载地址")
    private String iosUrl;
	/**是否强制升级*/
    @Excel(name = "是否强制升级", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "是否强制升级")
    private String forceUpdate;
	/**是否推送*/
    @Excel(name = "是否推送", width = 15,replace = {"是_Y","否_N"} )
    @ApiModelProperty(value = "是否推送")
    private String push;
}
