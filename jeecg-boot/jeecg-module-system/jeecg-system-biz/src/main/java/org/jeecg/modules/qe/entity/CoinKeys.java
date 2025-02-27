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
 * @Description: 用户平台个人信息
 * @Author: jeecg-boot
 * @Date:   2025-02-27
 * @Version: V1.0
 */
@Data
@TableName("coin_keys")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_keys对象", description="用户平台个人信息")
public class CoinKeys implements Serializable {
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
	/**apiKey*/
	@Excel(name = "apiKey", width = 15)
    @ApiModelProperty(value = "apiKey")
    private String apiKey;
	/**apiSecret*/
	@Excel(name = "apiSecret", width = 15)
    @ApiModelProperty(value = "apiSecret")
    private String apiSecret;
	/**memberId*/
	@Excel(name = "memberId", width = 15, dictTable = "sys_user", dicText = "username", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "username", dicCode = "id")
    @ApiModelProperty(value = "memberId")
    private String memberId;
	/**交易所平台*/
	@Excel(name = "交易所平台", width = 15, dicCode = "exchange")
	@Dict(dicCode = "exchange")
    @ApiModelProperty(value = "交易所平台")
    private String exchange;
	/**所属环境*/
	@Excel(name = "所属环境", width = 15, dicCode = "env")
	@Dict(dicCode = "env")
    @ApiModelProperty(value = "所属环境")
    private String env;
	/**止损点数*/
	@Excel(name = "止损点数", width = 15)
    @ApiModelProperty(value = "止损点数")
    private Double pointLoss;
	/**止盈点数*/
	@Excel(name = "止盈点数", width = 15)
    @ApiModelProperty(value = "止盈点数")
    private Double pointProfit;
	/**机器人总点数*/
	@Excel(name = "机器人总点数", width = 15)
    @ApiModelProperty(value = "机器人总点数")
    private Double bpTotal;
	/**剩余点数*/
	@Excel(name = "剩余点数", width = 15)
    @ApiModelProperty(value = "剩余点数")
    private Double bpPointFree;
	/**用户描述*/
	@Excel(name = "用户描述", width = 15)
    @ApiModelProperty(value = "用户描述")
    private String keyName;
	/**资金密码*/
	@Excel(name = "资金密码", width = 15)
    @ApiModelProperty(value = "资金密码")
    private String fundPwd;
}
