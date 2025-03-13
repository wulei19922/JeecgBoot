package org.jeecg.modules.wechat.controller;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.cashcoupons.CashCouponsService;
import com.wechat.pay.java.service.cashcoupons.model.*;
import com.wechat.pay.java.service.merchantexclusivecoupon.model.CouponUseRule;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;
import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/** JSAPI 下单为例 */
public class QuickStart {

    /** 商户号 */
    public static String merchantId = "1708581355";

    /** 商户API私钥路径 */
    public static String privateKeyPath = "D:\\file\\wechat\\1708581355_20250311_cert\\apiclient_key.pem";

    /** 商户证书序列号 */
    public static String merchantSerialNumber = "21CB8EAC28B84FE8E5E045B62576928820B5683E";

    /** 商户APIV3密钥 */
    public static String apiV3Key = "U2k85rt6elX94Lqp9i2pB60v0J78g7rW";

    public static void qeryMerchant() {
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(merchantId)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();
        CashCouponsService cashCouponsService = new CashCouponsService.Builder().config(config).build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        CreateCouponStockRequest request = new CreateCouponStockRequest();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        ListAvailableMerchantsRequest req=new ListAvailableMerchantsRequest();
        req.setLimit(10);
        req.setOffset(1);
        AvailableMerchantCollection availableMerchantCollection = cashCouponsService.listAvailableMerchants(req);
        System.out.println(availableMerchantCollection.toString());
    }

    public static void createStocke() {
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(merchantId)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .apiV3Key(apiV3Key)
                        .build();
        CashCouponsService cashCouponsService = new CashCouponsService.Builder().config(config).build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        CreateCouponStockRequest request = new CreateCouponStockRequest();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        // 当前时间（带时区）
        OffsetDateTime start = OffsetDateTime.now().plus(Duration.ofDays(1));
        OffsetDateTime end = OffsetDateTime.now().plus(Duration.ofDays(2));
        request.setAvailableBeginTime(start.format(formatter));
        request.setAvailableEndTime(end.format(formatter));
//        request.setComment("测试批次");
        request.setStockName("建设银行2025");
        request.setBelongMerchant("1708581355");
        StockRule rule=new StockRule();
        rule.setMaxCoupons(60000L);
        rule.setMaxAmount(600000L);
        rule.setMaxAmountByDay(600000L);
        rule.setMaxCouponsPerUser(10);
        rule.setNaturalPersonLimit(false);
        rule.setPreventApiAbuse(false);


        request.setStockUseRule(rule);

        PatternInfo patternInfo=new PatternInfo();
        patternInfo.setDescription("零售批次");
        patternInfo.setMerchantLogo("https://h5.runnongji.com/static/images/public/logo.png");
        patternInfo.setMerchantName("微信支付代金券批次");
        patternInfo.setBackgroundColor(BackgroundColor.COLOR010);
        patternInfo.setCouponImage("https://h5.runnongji.com/static/images/public/logo.png");

//        request.setPatternInfo(patternInfo);



        CouponRule couponUseRule=new CouponRule();
        List<String> availableMerchants=new ArrayList<>();
        availableMerchants.add("98568865");
        couponUseRule.setAvailableMerchants(availableMerchants);
        FixedValueStockMsg fixedValueStockMsg=new FixedValueStockMsg();
        fixedValueStockMsg.setCouponAmount(10L);
        fixedValueStockMsg.setTransactionMinimum(10L);
        couponUseRule.setFixedNormalCoupon(fixedValueStockMsg);

        FavorAvailableTime favorAvailableTime=new FavorAvailableTime();
        FixedAvailableTime fixedAvailableTime=new FixedAvailableTime();
        fixedAvailableTime.setAvailableWeekDay(Arrays.asList(1));
        fixedAvailableTime.setBeginTime(0);
        fixedAvailableTime.setEndTime(3600);

        favorAvailableTime.setFixAvailableTime(fixedAvailableTime);

        favorAvailableTime.setSecondDayAvailable(false);
        favorAvailableTime.setAvailableTimeAfterReceive(7);
        couponUseRule.setCouponAvailableTime(favorAvailableTime);

//        couponUseRule.setGoodsTag(Arrays.asList("1"));
//        couponUseRule.setTradeType(Arrays.asList(TradeType.MICROAPP));
//        couponUseRule.setCombineUse(Boolean.TRUE);
//        couponUseRule.setAvailableItems(Arrays.asList("123321"));
//        CardLimitation cardLimitation=new CardLimitation();
//
//        cardLimitation.setBin(Arrays.asList("62542688"));
//        cardLimitation.setName("精粹白金");
//
//        couponUseRule.setLimitCard(cardLimitation);
//        couponUseRule.setLimitPay(Arrays.asList("BCZ_DEBIT"));
        request.setCouponUseRule(couponUseRule);


        request.setNoCash(false);
        request.setStockType("NORMAL");

        request.setOutRequestNo("1708581355_202503112312_0000001");
        request.setExtInfo("");


        CreateCouponStockResponse couponStock = cashCouponsService.createCouponStock(request);
        System.out.println(couponStock.getStockId());
    }


    public static void main(String[] args) {

        createStocke();

    }
}