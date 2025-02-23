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
 * @Description: 用户平台密钥对
 * @Author: jeecg-boot
 * @Date:   2025-02-24
 * @Version: V1.0
 */
@Data
@TableName("coin_keys")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coin_keys对象", description="用户平台密钥对")
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
	@Excel(name = "memberId", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "id")
	@Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
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
}
