package org.jeecg.modules.qe.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chopper
 */
@Data
@ApiModel(value = "公告数据")
@NoArgsConstructor
public class ChargeAddress {


    @ApiModelProperty("coin")
    private String coin;

    @ApiModelProperty("address")
    private String address;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("url")
    private String uid;


}