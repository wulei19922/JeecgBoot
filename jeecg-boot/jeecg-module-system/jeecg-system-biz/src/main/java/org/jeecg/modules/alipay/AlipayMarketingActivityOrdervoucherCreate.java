package org.jeecg.modules.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.domain.VoucherDisplayPatternInfo;
import com.alipay.api.domain.VoucherDeductInfo;
import com.alipay.api.domain.VoucherUseGuideInfo;
import com.alipay.api.domain.VoucherSendModeInfo;
import com.alipay.api.domain.VoucherUseTimeInfo;
import com.alipay.api.domain.VoucherUseRuleInfo;
import com.alipay.api.domain.VoucherCustomerGuideInfo;
import com.alipay.api.domain.VoucherMiniAppUseGuideInfo;
import com.alipay.api.request.AlipayMarketingActivityOrdervoucherCreateRequest;
import com.alipay.api.domain.DiscountVoucherInfo;
import com.alipay.api.domain.VoucherSendRuleInfo;
import com.alipay.api.domain.VoucherAbsolutePeriodInfo;
import com.alipay.api.domain.ActivityBaseInfo;
import com.alipay.api.response.AlipayMarketingActivityOrdervoucherCreateResponse;
import com.alipay.api.domain.VoucherAvailableGeographyScopeInfo;
import com.alipay.api.domain.VoucherAvailableScopeInfo;
import com.alipay.api.domain.VoucherAvailableGeographyCityInfo;
import com.alipay.api.domain.AlipayMarketingActivityOrdervoucherCreateModel;

import com.alipay.api.FileItem;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AlipayMarketingActivityOrdervoucherCreate {

    public static void main(String[] args) throws AlipayApiException, ParseException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        // 构造请求参数以调用接口
        AlipayMarketingActivityOrdervoucherCreateRequest request = new AlipayMarketingActivityOrdervoucherCreateRequest();
        AlipayMarketingActivityOrdervoucherCreateModel model = new AlipayMarketingActivityOrdervoucherCreateModel();

        // 设置活动基础信息
        ActivityBaseInfo activityBaseInfo = new ActivityBaseInfo();
        activityBaseInfo.setActivityName("五折优惠券");
        activityBaseInfo.setCodeMode("MERCHANT_UPLOAD");
        model.setActivityBaseInfo(activityBaseInfo);

        // 设置商户接入模式
        model.setMerchantAccessMode("SELF_MODE");


        // 设置外部业务单号
        model.setOutBizNo("20170101000001654bb46ba");

        // 设置券可用范围
        VoucherAvailableScopeInfo voucherAvailableScopeInfo = new VoucherAvailableScopeInfo();
        VoucherAvailableGeographyScopeInfo voucherAvailableGeographyScopeInfo = new VoucherAvailableGeographyScopeInfo();
        VoucherAvailableGeographyCityInfo availableGeographyCityInfo = new VoucherAvailableGeographyCityInfo();
        availableGeographyCityInfo.setAllCity(true);
        voucherAvailableGeographyScopeInfo.setAvailableGeographyCityInfo(availableGeographyCityInfo);
        voucherAvailableGeographyScopeInfo.setAvailableGeographyScopeType("CITY_CODE");
        voucherAvailableScopeInfo.setVoucherAvailableGeographyScopeInfo(voucherAvailableGeographyScopeInfo);
        model.setVoucherAvailableScopeInfo(voucherAvailableScopeInfo);

        // 设置券引导详情
        VoucherCustomerGuideInfo voucherCustomerGuideInfo = new VoucherCustomerGuideInfo();
        VoucherUseGuideInfo voucherUseGuideInfo = new VoucherUseGuideInfo();
        VoucherMiniAppUseGuideInfo miniAppUseGuideInfo = new VoucherMiniAppUseGuideInfo();
        miniAppUseGuideInfo.setMiniAppUrl("alipays://platformapi/startapp?appId=2021005128697805");
        voucherUseGuideInfo.setMiniAppUseGuideInfo(miniAppUseGuideInfo);
        List<String> useGuideMode = new ArrayList<String>();
        useGuideMode.add("MINI_APP");
        voucherUseGuideInfo.setUseGuideMode(useGuideMode);
        voucherCustomerGuideInfo.setVoucherUseGuideInfo(voucherUseGuideInfo);
        model.setVoucherCustomerGuideInfo(voucherCustomerGuideInfo);

        // 设置券优惠抵扣信息
        VoucherDeductInfo voucherDeductInfo = new VoucherDeductInfo();
        DiscountVoucherInfo discountVoucherInfo = new DiscountVoucherInfo();
        discountVoucherInfo.setCeilingAmount("1300");
        discountVoucherInfo.setDiscount("5");
        discountVoucherInfo.setFloorAmount("500");
        voucherDeductInfo.setDiscountVoucherInfo(discountVoucherInfo);
        voucherDeductInfo.setVoucherType("DISCOUNT_VOUCHER");
        model.setVoucherDeductInfo(voucherDeductInfo);

        // 设置券展示信息
        VoucherDisplayPatternInfo voucherDisplayPatternInfo = new VoucherDisplayPatternInfo();
//        voucherDisplayPatternInfo.setBrandLogo("A*Ub5zTofIEHAAAAAAAAAAAAAAARwnAQ");
        voucherDisplayPatternInfo.setBrandName("商家优惠");
        voucherDisplayPatternInfo.setVoucherDescription("五折优惠券");
        model.setVoucherDisplayPatternInfo(voucherDisplayPatternInfo);

        // 设置券发放模式信息
        VoucherSendModeInfo voucherSendModeInfo = new VoucherSendModeInfo();
        voucherSendModeInfo.setVoucherSendMode("DIRECT_SEND_MODE");
        VoucherSendRuleInfo voucherSendRuleInfo = new VoucherSendRuleInfo();
        voucherSendRuleInfo.setPublishEndTime(sdf.parse("2025-03-30 23:59:59"));
        voucherSendRuleInfo.setPublishStartTime(sdf.parse("2025-03-24 00:00:00"));
        voucherSendRuleInfo.setQuantity(0L);
        voucherSendRuleInfo.setQuantityLimitPerUser(0L);
        voucherSendModeInfo.setVoucherSendRuleInfo(voucherSendRuleInfo);
        model.setVoucherSendModeInfo(voucherSendModeInfo);

        // 设置券核销限制
        VoucherUseRuleInfo voucherUseRuleInfo = new VoucherUseRuleInfo();
        VoucherUseTimeInfo voucherUseTimeInfo = new VoucherUseTimeInfo();
        VoucherAbsolutePeriodInfo absolutePeriodInfo = new VoucherAbsolutePeriodInfo();
        absolutePeriodInfo.setValidBeginTime(sdf.parse("2025-03-24 23:59:59"));
        absolutePeriodInfo.setValidEndTime(sdf.parse("2025-04-01 00:00:00"));
        voucherUseTimeInfo.setAbsolutePeriodInfo(absolutePeriodInfo);
        voucherUseTimeInfo.setPeriodType("ABSOLUTE");
        voucherUseRuleInfo.setVoucherUseTimeInfo(voucherUseTimeInfo);
        model.setVoucherUseRuleInfo(voucherUseRuleInfo);

        request.setBizModel(model);
        AlipayMarketingActivityOrdervoucherCreateResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
    }

    private static AlipayConfig getAlipayConfig() {
//        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCOHvtUeQX76s7a+ef/+Vr/xXDoJjGLmqPEbqEOXh23Nf96DWFd7DPG6FizJd3aKjG90rgO2k5qBsSFPLYEZes+/405aC1nH5ht3WsHb9+antgI5iUg58xkOuqIZaMdUlpbBVqq4vc+rDPx+AWFaSn9wJTEZSWRrZ4Gr1y8ilhHuDqvS6AwaWxxz4psrGaIa7OBE2Yhv3Z8uy8yWqlyD/bxBj00JLlffMvGt4yLMTUqd0mgm6x6BMvv7kfBrkRPgQVFGS3KYP+rpBFg5xilYdFNFjlEweNLJRHom3UEj+90q7LuzEKdeGPuhEjZVQIHZ3eCoVsuJjPTB5jNvqvEuC6RAgMBAAECggEAV3BGTy16egh4Jg91Jm7DBvPxyFKfTvXgZh1uZ4mOHMdTHEOZMRD+thUDZY0Zln05R0GazkCP1/m79QFqNwCBoKvXtoca0ASHYqjEjbnNx7KM1cTK+RjEDrKU00A8//BH+RItO1ASr/OArhFbTD5sgFt46/ATEjLQM+q2UqWHNO//uMTW8sQFMa8ewDC8qfT7KM1RhV/UVP//ze7Np90qPxtUFgyK4UVgE7PFSFhnsZaABC4ZF0CnoGlG29lHLURTi8tzJf2u8/HEo472ukeAYzZTv4DcsGnFS5oEnYZimFcVB+j7DaJxQ6BN4KuPsY/p+qpOKtPTxdhO76PuQobi8QKBgQDLAfNwd0SbxkUfOTzhhIvHesvyBdOKTPN0fpobUVqnB3KNC4vv4SsRmlyAqc1KJd/euQk6xckq0j7WC2uESJp8KsdnNX9/eaJ0YFgFfGy7qIXz4W/n8i62Fyqtixcw3YsY9lgvlN325EN8e2OScnB5OKGfB3XzQaUVSyU7cyNaDQKBgQCzOEOzKEpbd/Ciu+ykJx7aXoPhujN9rroxwoxF/FKvcd/oQAIUhXXWorPwhYpknW5PXUueaWx3jf6IVtPLQUU+2ZQFpNSrcOoZW+LZRi/ae/T0WyuoYfs+MOwBqptgtw1rKlZ7TnVrAGbyUL2bNJa4IN42inhdNEaHS2UuK9+ZlQKBgHGdJ+c7eLRNpIWy/PWhvrDncpiC1j9CjcYWzJyZTUjJjDyPx8N6myYA4+BGJ9RlmgMGP7oa01kZzXzxazzffUYckviaIj7vjhHfkaOQXTIxQLaa5oxDKA1UrdfuTg5VDvHrfLM0d30jhvl1/OYLEhNltF5SSJrZlFB0e+PnrlNJAoGAUEOnlko6GwL5Y3h0gAQBPl1MBUEmJGinfPebaRCbW3XNiK95/wTShKDzo4m7NFqJd8PZxQWEDXsrfpYcXpBJ2d8J+wkaMfns7hSH4TUc42XWi8V1JQFUy0KPwCNPWR5CRTd96dV2oshYUre0YxlHUwltW9HiHCP4Xkl29Bm0F40CgYBplBMn0/JskqbGlMyKNByQ9oUwpENpq30X0PBcidTLbKOJ6Qvmwctc7PDxNJfx35p1+LPyJcUpwigbyhTCB7X/IHme+jSPcB7HybK6Il5nFkobgGCBqHTVcW77f0hCl92OESqWVZapyRVNCPQot6cBPfsw3N6Wf8ZluBv3bt6U0A==";
//        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhtVI4f6uWAM6h3HcNxCMgHw3ofws/UKA75Mmu2TsH5GJq07UNEiVweTk3cB6EHpDIhbosMTCkAf7EHdQNaVYaGIop807VL5ElN+SXScn5kKafzbrGDhpas5RQmSTJcKFz58/GsoINuKPfhIXNuCHdu211h2qwkrO78NysTJNjYpLHcIJuccd7eqlYpSxg9bHZ3x1OTIFKaTU6fWSYyR3qy2lQYxhyDg97R+d/Pw+Tc777x080BizdFFdWKu8nI8u9Abfmb2+MAP8frPO5QbNJbNOGANUVJ+G+ue+6ZvjZzpHYdAVMzRdvs6NEKIWqayOSgR4ockaLamHyeN5sQWVTwIDAQAB";

        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhtVI4f6uWAM6h3HcNxCMgHw3ofws/UKA75Mmu2TsH5GJq07UNEiVweTk3cB6EHpDIhbosMTCkAf7EHdQNaVYaGIop807VL5ElN+SXScn5kKafzbrGDhpas5RQmSTJcKFz58/GsoINuKPfhIXNuCHdu211h2qwkrO78NysTJNjYpLHcIJuccd7eqlYpSxg9bHZ3x1OTIFKaTU6fWSYyR3qy2lQYxhyDg97R+d/Pw+Tc777x080BizdFFdWKu8nI8u9Abfmb2+MAP8frPO5QbNJbNOGANUVJ+G+ue+6ZvjZzpHYdAVMzRdvs6NEKIWqayOSgR4ockaLamHyeN5sQWVTwIDAQAB";
        // 应用私钥
        String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCQtv05ACoYw91m2W4fca7dXO5icP8xCP+9nyBggRM6QCsziicnnEVmsrnH51lk1xn4TDJU+4Hinqt8YpqD1isP9ZKtLHwrGCwbTEedwHRB7RKzpphkCrmmGWj+59oJGNNlsZblWxFxvGicMaPdxovcN+29U6+KZEb9NikU1WMh9eUM+4TrzzCWOmsSRilXHD9IKPri52paM1ZLiTXNshdORA1H35wSLgpU1l0f0sj/3vmUs/SVJuGBGIoDDuHsmk3/bo8uP2HVpPkEG+wB+x160d0rHyrgFtZCtr2JQdFCbP4JQuFQv5q6vKsWTpy0xxIhZHzNmAArrAdIhr9rYJ3dAgMBAAECggEBAIOvQDXCRIxMjwesC11poWmlyvahRFZmjfEK//dQ/SIgii9YI9ETjsOpFw0lJF9yrRsYmyRhyQtiv0FsqJYXKRBYsqQKuuKsGFauzuxp2GC1TjNUAY/ngo/2x2hNqkZk5NjBBUKn3/PTMo4PTcHdjh9SdW+22yHfpgl8Y12l35NiQNnh5Rzy8wArWHq1cr3csyFQOb9HG4bV00fOk7DvHjlo57/IXfWISvGPsJzECBz0XeBgiYo7qMEAf5o0bboThWdKazRsbV7vJVHFJEUHCFgE9jvSsSnK5Fd7iXm6Qwijmz3rOiePlIFC1rARSPb2keDwITV7dQr9p6KNRga99AECgYEA3UsY5AGsIIv5dpRbLQUuQyAoqsFLArWuuS1dZ6dgSkZ3mOlR0pilbPGHk59K4BKanmmc8+7qe2aKb+qhOxkImD0t+wmKI5KvIRMiSVwVop4HUaIrGCozxnwaKPYfk8jHAAwMCB5/NFYHlAVUX7h3WNXyC9XjLlRDJVzbbRcsncECgYEAp2lFn2z6fPnCeM0v+Mkqk5yPQ6Z1Qq7DYZjDTkNt4PsPxsTtBrKiWhH+F48JmPDaiYsme36rT0OAjTJ1jPBx9p4+62kZ20qmkI7m5KkcP79SB8wEyxJaB3jc1Fvlc71gQwsuQz+jKpVw56mOwvOOaTHjYb9QfzANF9YMGA0Qfx0CgYEAwndUEEpH8PIQ71vAI/gBh4ujMStbt63hNWngQ06bF6qFPyMKfH8YIHzAH8JEElVG+x7khpjWBks+LMuLlFuTafWZipn/gvNMY0xlPQJnINiupdZU+I9doHc+pfBkH4K5Hhl4GUpzjpJrZnCojNWWS8L3Sd9UHUp/Fz5atas/0gECgYA2bNj/nXRJ0QDSORLRdMTsvEcvHRyeEybdwcYs2WsNa1GTs03DkauKULMkgZIDMLo7YQuftmyHFlDilnb03O6P2J5n9gE786b29B5IVFokt1Q5yK/+PPcb2sUgHMgZuoVjBm2B21x2aaMNDWimJHw14swjaQkIcFyxVRsVbpngWQKBgQDEvYczB0xZrpsUr8EavXW4fBYATF28q3w6Kwfs8+xcYTE6gNh/mm2kB5IvHSbeivmK49Tq1g43zJEgffgs6gPZuLBaL/uIkdeYaf1R7UGY9qpND0TEENa4pYD0YAbM4qA3BXD6gUjQQIspLaFpn4BWAoynPdQts5KHyQwroRRuNA==";

        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId("2021005129668679");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }
}