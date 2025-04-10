package org.jeecg.modules.wechat.controller;
import org.jeecg.modules.demo.m.entity.VoucherWechatBank;
import org.jeecg.modules.demo.m.service.IVoucherWechatBankService;
import org.springframework.web.client.RestTemplate;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import netscape.javascript.JSObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.m.controller.VouchersWechatController;
import org.jeecg.modules.demo.m.entity.MarketVouchers;
import org.jeecg.modules.demo.m.service.IMarketVouchersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "激活工具")
@RequestMapping("/wechat")
public class VoucherWechatController {
    @Autowired
    private IVoucherWechatBankService wechatBankService;

    @ApiOperation(value = "支付营销代金券场景-微信平台", notes = "支付营销代金券场景-微微信平台-创建")
    @GetMapping(value = "/create")
    public Result<JSObject> create() {
        return Result.OK("");
    }

    @ApiOperation(value = "支付营销代金券场景-微信平台", notes = "支付营销代金券场景-微微信平台-激活")
    @GetMapping(value = "/active")
    public Result<JSObject> active(@RequestParam String id) {
        //修改状态为激活中
        VoucherWechatBank wechatBank = wechatBankService.getById(id);
        wechatBank.setStatus("20");
        boolean b = wechatBankService.updateById(wechatBank);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:5000/active?id="+wechatBank.getId();
        String response = restTemplate.getForObject(url, String.class);
        if (b) {
            //调用python脚本
            //开始调度
            // 发送GET请求
            return Result.OK("");
        } else {

            return Result.OK("");
        }


    }

    @ApiOperation(value = "支付营销代金券场景-微信平台", notes = "支付营销代金券场景-微微信平台-暂停")
    @GetMapping(value = "/stop")
    public Result<JSObject> stop() {
        return Result.OK("");
    }

    @ApiOperation(value = "支付营销代金券场景-微信平台", notes = "支付营销代金券场景-微微信平台-重启")
    @GetMapping(value = "/restart")
    public Result<JSObject> restart() {
        return Result.OK("");
    }

    @ApiOperation(value = "支付营销代金券场景-微信平台", notes = "支付营销代金券场景-微微信平台-详情")
    @GetMapping(value = "/detail")
    public Result<JSObject> detail() {
        return Result.OK("");
    }
}
