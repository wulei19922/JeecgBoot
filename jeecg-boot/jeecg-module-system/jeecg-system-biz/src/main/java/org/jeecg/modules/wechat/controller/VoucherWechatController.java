package org.jeecg.modules.wechat.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import netscape.javascript.JSObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.m.entity.MarketVouchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/wechat")
public class VoucherWechatController {

    @ApiOperation(value="支付营销代金券场景-微信平台", notes="支付营销代金券场景-微微信平台-创建")
    @GetMapping(value = "/create")
    public Result<JSObject> create() {
        return Result.OK("");
    }

    @ApiOperation(value="支付营销代金券场景-微信平台", notes="支付营销代金券场景-微微信平台-激活")
    @GetMapping(value = "/active")
    public Result<JSObject> active() {
        return Result.OK("");
    }

    @ApiOperation(value="支付营销代金券场景-微信平台", notes="支付营销代金券场景-微微信平台-暂停")
    @GetMapping(value = "/stop")
    public Result<JSObject> stop() {
        return Result.OK("");
    }

    @ApiOperation(value="支付营销代金券场景-微信平台", notes="支付营销代金券场景-微微信平台-重启")
    @GetMapping(value = "/restart")
    public Result<JSObject> restart() {
        return Result.OK("");
    }

    @ApiOperation(value="支付营销代金券场景-微信平台", notes="支付营销代金券场景-微微信平台-详情")
    @GetMapping(value = "/detail")
    public Result<JSObject> detail() {
        return Result.OK("");
    }
}
