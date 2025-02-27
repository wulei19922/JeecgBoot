package org.jeecg.modules.qe.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.qe.FundsChangeEnum;
import org.jeecg.modules.qe.entity.*;
import org.jeecg.modules.qe.mapper.CoinFundsChangeMapper;
import org.jeecg.modules.qe.service.ICoinKeysService;
import org.jeecg.modules.qe.service.ICoinWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BinanceWithDrawService {

    @Autowired
    CoinSupportServiceImpl coinSupportService;

    @Autowired
    ICoinKeysService iCoinKeysService;


    @Autowired
    RedisUtil redisUtil;

    @Value("${coin.wallet.account}")
    String manager;

    @Autowired
    CoinWalletServiceImpl coinWalletService;


    @Autowired
    CoinFundsChangeServiceImpl coinFundsChangeService;

    @Autowired
    CoinFundsChangeMapper coinFundsChangeMapper;



    //获得财务收款账号地址

    public CoinWallet getWallet(String id) {
        LambdaQueryWrapper<CoinWallet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CoinWallet::getMemberId, id);
        List<CoinWallet> list = coinWalletService.list(queryWrapper);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    public CoinKeys getKeysCoinConfig(String id) {
        QueryWrapper<CoinKeys> queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id", id);
        queryWrapper.eq("env", "prod");
        queryWrapper.eq("exchange", "BINANCE");
        List<CoinKeys> res = iCoinKeysService.list(queryWrapper);
        if (!res.isEmpty()) {
            return res.get(0);
        }
        return null;
    }

    public CoinWallet getCoinWallet(String memberid){

        //获得当前用户钱包
        LambdaQueryWrapper<CoinWallet>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CoinWallet::getMemberId, memberid);
        List<CoinWallet> list = coinWalletService.list(queryWrapper);
        if(!list.isEmpty()){
            return  list.get(0);
        }
        return null;
    }
    public boolean withTrade(String memberid, BigDecimal amount) {

        try {
            //获得目标配置
            CoinWallet managerWallet = getWallet(manager);
            CoinKeys manageCoinConfig = getKeysCoinConfig(manager);
            //获得源配置
            CoinWallet origin = getWallet(memberid);
            CoinKeys originCoinConfig = getKeysCoinConfig(memberid);

            String originApiKey = originCoinConfig.getApiKey();
            String originSecretKey = originCoinConfig.getApiSecret();

            String managerApiKey = manageCoinConfig.getApiKey();
            String managerSecretKey = manageCoinConfig.getApiSecret();

            SpotClient originSpotClient = new SpotClientImpl(originApiKey, originSecretKey);
            SpotClient managerMainSpotClient = new SpotClientImpl(managerApiKey, managerSecretKey);
            Map param = new HashMap();
            param.put("recvWindow", 2000);
            param.put("omitZeroBalances", true);
            param.put("timestamp", System.currentTimeMillis());
            //获得交易设置

            // 获取主账户USDT余额
            String mainAccountStr = originSpotClient.createTrade().account(param);
            JSONObject mainAccount = JSONObject.parseObject(mainAccountStr);
            double currentBanance = getUSDTBalance(mainAccount);
            log.info("当前用户" + memberid + " USDT余额（转账前）： " + currentBanance);

            Map otherparam = new HashMap();
            otherparam.put("recvWindow", 4000);
            otherparam.put("omitZeroBalances", true);
            otherparam.put("timestamp", System.currentTimeMillis());

            Map add = new HashMap();
            add.put("coin", "USDT");
            add.put("network", "TRX");

            String addressDetail = managerMainSpotClient.createWallet().depositAddress(add);
            JSONObject address = JSONObject.parseObject(addressDetail);
            log.info("管理账户充值地址： " + addressDetail);

            Map tradeParam = new HashMap();
            tradeParam.put("coin", address.getString("coin"));
            tradeParam.put("address", address.getString("address"));
            tradeParam.put("network", "TRX");
            tradeParam.put("amount", amount.doubleValue());
            tradeParam.put("transactionFeeFlag", true);
            String drawdetail = originSpotClient.createWallet().withdraw(tradeParam);
            log.info("转账提交 "+drawdetail);
            JSONObject drawdetailObject = JSONObject.parseObject(drawdetail);
            String orderId = drawdetailObject.getString("id");

            Map detailParam = new HashMap();
            detailParam.put("idList", orderId);

            String detail = originSpotClient.createWallet().withdrawHistory(detailParam);
            Map paramAfter = new HashMap();
            paramAfter.put("recvWindow", 2000);
            paramAfter.put("omitZeroBalances", true);
            paramAfter.put("timestamp", System.currentTimeMillis());
            //获得交易设置

            // 获取主账户USDT余额
            String mainAccountStrAfter = originSpotClient.createTrade().account(paramAfter);
            JSONObject mainAccountAfter = JSONObject.parseObject(mainAccountStrAfter);
            double usdtBalanceAfter = getUSDTBalance(mainAccountAfter);
            log.info("当前用户" + memberid + " USDT余额（转账后）： " + usdtBalanceAfter);

            coinWalletService.updateUserWallet(manager,memberid,usdtBalanceAfter,currentBanance,amount);
            coinFundsChangeMapper.userWalletAdd(amount.doubleValue(),memberid);
            return true;
        } catch (Exception e) {
            log.error("转账失败", e);
        }

        return false;
    }

    public ChargeAddress getChargeAddress(String id) {
        String baseUrl = "";
        CoinKeys coinKeys = getKeysCoinConfig(id);
        baseUrl = "https://api.binance.com";
        // 替换为你的API密钥和私钥
        String apiKey = coinKeys.getApiKey();
        String secretKey = coinKeys.getApiSecret();

        // 创建SpotClient实例
        SpotClient client = new SpotClientImpl(apiKey, secretKey, baseUrl);

        // 设置请求参数
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("coin", "USDT"); // 替换为需要的币种

        try {

            // （可选）尝试生成存款地址，指定币种（如 BTC）
            LinkedHashMap<String, Object> depositParams = new LinkedHashMap<>();
            depositParams.put("coin", "USDT"); // 指定币种
            String depositAddress = client.createWallet().depositAddress(depositParams);
            JSONObject jsonObject = JSON.parseObject(depositAddress);


//            System.out.println("充值地址: " + address);
            ChargeAddress ad = new ChargeAddress();
            ad.setAddress(jsonObject.getString("address"));
            ad.setCoin("USDT");
            ad.setUrl(jsonObject.getString("url"));
            return ad;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;


    }

    private static double getUSDTBalance(JSONObject account) {
        JSONArray balances = account.getJSONArray("balances");

        for (Object o : balances) {
            JSONObject oj = (JSONObject) o;
            if (oj.getString("asset").equals("USDT")) {
                return oj.getDouble("free");
            }

        }
        return 0.0;
    }

    public static void main(String[] args) {

        String mainApiKey = "5tiDiv3NI0U107lFfBdenGZNkdNElakAdFuCd3mQGW3S9IbI7gQNHLLLMFEfwy9h";
        String mainSecretKey = "TVPiuFyWkundIXJrXbce4TXwkhDWmoMm0xBe124ETr0rP8NK7yCrYL9ZqHnEK6Nn";

        String otherMainApiKey = "5vQGewWTDLzz8HBvbmwYo0ahmqDCQtk8u82jlBwzGhScKiqepTgetao4m7OjCzBv";
        String otherMainSecretKey = "Az9dXjnwXPgxu7NmUyDx956DGltcKvvOYwqqLT6Ajcmu0VdSnBtonNRCIScbfGqz";
        String otherMainAccountAddress = "other_main_account_usdt_address";

        SpotClient mainSpotClient = new SpotClientImpl(mainApiKey, mainSecretKey);
        SpotClient otherMainSpotClient = new SpotClientImpl(otherMainApiKey, otherMainSecretKey);
        Map param = new HashMap();
        param.put("recvWindow", 2000);
        param.put("omitZeroBalances", true);
        param.put("timestamp", System.currentTimeMillis());
        //获得交易设置

//        String s = mainSpotClient.createWallet().assetDetail(param);
//        System.out.println("交易设置"+s);
        // 获取主账户USDT余额
        String mainAccountStr = mainSpotClient.createTrade().account(param);
        System.out.println(mainAccountStr);
        JSONObject mainAccount = JSONObject.parseObject(mainAccountStr);
        double mainUSDTBalance = getUSDTBalance(mainAccount);
        System.out.println("主账户USDT余额（转账前）： " + mainUSDTBalance);

        Map otherparam = new HashMap();
        otherparam.put("recvWindow", 4000);
        otherparam.put("omitZeroBalances", true);
        otherparam.put("timestamp", System.currentTimeMillis());
        String otherAccountStr = otherMainSpotClient.createTrade().account(otherparam);
        System.out.println(otherAccountStr);
        JSONObject otherAccount = JSONObject.parseObject(otherAccountStr);
        double otherUSDTBalance = getUSDTBalance(otherAccount);
        System.out.println("其他USDT余额（转账前）： " + otherUSDTBalance);

        Map add = new HashMap();
        add.put("coin", "USDT");
        add.put("network", "TRX");

        String addressDetail = otherMainSpotClient.createWallet().depositAddress(add);
        JSONObject address = JSONObject.parseObject(addressDetail);
        System.out.println("miaoyan账户充值地址： " + addressDetail);
//        LinkedHashMap<String, Object> depositParams = new LinkedHashMap<>();
//        depositParams.put("coin", "USDT"); // 指定币种
//        String depositAddress = client.createWallet().depositAddress(depositParams);
//        JSONObject jsonObject = JSON.parseObject(depositAddress);


        Map tradeParam = new HashMap();
        tradeParam.put("coin", address.getString("coin"));
        tradeParam.put("address", address.getString("address"));
        tradeParam.put("network", "TRX");
        tradeParam.put("amount", "0.001");
        tradeParam.put("transactionFeeFlag", true);
//        // 向另一个主账户转账20 USDT
//         String drawdetail=mainSpotClient.createWallet().withdraw(tradeParam);
//         System.out.println("转账详情"+drawdetail);


        Map detailParam = new HashMap();
//        detailParam.put("coin", "USDT");
//        detailParam.put("withdrawOrderId", "73fe9b56490740578f8bd7e62a35e1ef");
//        detailParam.put("status", 0);
        detailParam.put("idList", "73fe9b56490740578f8bd7e62a35e1ef");


        String detail = mainSpotClient.createWallet().withdrawHistory(detailParam);

        System.out.println("订单详情  " + detail);


    }


}
