package org.jeecg.modules.qe.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.qe.entity.ChargeAddress;
import org.jeecg.modules.qe.entity.CoinKeys;
import org.jeecg.modules.qe.entity.CoinSupport;
import org.jeecg.modules.qe.entity.TickerResutl;
import org.jeecg.modules.qe.mapper.CoinKeysMapper;
import org.jeecg.modules.qe.service.ICoinKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import com.binance.connector.client.SpotClient;

@Service
public class BinanceClientService {

    @Autowired
    CoinSupportServiceImpl coinSupportService;

    @Autowired
    ICoinKeysService iCoinKeysService;


    @Autowired
    RedisUtil redisUtil;


    public List<TickerResutl> getTopList(boolean isUp) {

//        if(redisUtil.hasKey("QUETO_TOP")){
//            String result= redisUtil.get("QUETO_TOP").toString();
//            List<TickerResutl> tickerResutls = JSON.parseArray(result, TickerResutl.class);
//            return  tickerResutls;
//        }

        QueryWrapper<CoinSupport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("up", "Y");
        queryWrapper.eq("trade_type", "SPOT");


        List<CoinSupport> supportsList = coinSupportService.list(queryWrapper);
        ArrayList symbols = new ArrayList();

        supportsList.stream().forEach(t -> {

            t.setSymbol(t.getSymbol().toUpperCase(Locale.ROOT));
            symbols.add(t.getSymbol());
        });
        // 初始化客户端（这里使用公开数据不需要API密钥）
        SpotClientImpl client = new SpotClientImpl();
        List<TickerResutl> result = supportsList.stream().map(t -> {
            TickerResutl tickerResutl = new TickerResutl();
            tickerResutl.setBaseCoin(t.getSymbol());
            tickerResutl.setIcon(t.getIcourl());
            return tickerResutl;

        }).collect(Collectors.toList());

        Map symbolParams = new HashMap<>();
        symbolParams.put("symbols", symbols);
        String priceResult = client.createMarket().ticker(symbolParams);
        JSONArray currentPrices = JSON.parseArray(priceResult);
        currentPrices.stream().forEach(
                t -> {
                    JSONObject one = JSON.parseObject(t.toString());
                    TickerResutl rone = result.stream().filter(r -> r.getBaseCoin().equals(one.getString("symbol"))).findFirst().get();
                    rone.setPriceUsd(one.getFloat("lastPrice"));
                    rone.setOpen(one.getFloat("openPrice"));
                    rone.setClose(one.getFloat("lastPrice"));
                    rone.setVol(one.getFloat("volume"));
                }
        );

        symbolParams.put("symbols", symbols);
        String ticker24hResult = client.createMarket().ticker24H(symbolParams);
        JSONArray h24Prices = JSON.parseArray(ticker24hResult);
        h24Prices.stream().forEach(
                t -> {
                    JSONObject one = JSON.parseObject(t.toString());
                    TickerResutl rone = result.stream().filter(r -> r.getBaseCoin().equals(one.getString("symbol"))).findFirst().get();
                    rone.setChange24Hours(one.getFloat("priceChange"));
                }
        );
        redisUtil.set("QUETO_TOP",JSON.toJSON(result),2000);
        return result;
    }


    public List<TickerResutl> getList(boolean isUp) {

        if(redisUtil.hasKey("QUETO")){
            String result= redisUtil.get("QUETO").toString();
            List<TickerResutl> tickerResutls = JSON.parseArray(result, TickerResutl.class);
            return  tickerResutls;
        }

        QueryWrapper<CoinSupport> queryWrapper = new QueryWrapper<>();
        if (isUp) {
            queryWrapper.eq("up", "Y");
        }

        List<CoinSupport> supportsList = coinSupportService.list(queryWrapper);
        ArrayList symbols = new ArrayList();

        supportsList.stream().forEach(t -> {

            t.setSymbol(t.getSymbol().toUpperCase(Locale.ROOT));
            symbols.add(t.getSymbol());
        });
        // 初始化客户端（这里使用公开数据不需要API密钥）
        SpotClientImpl client = new SpotClientImpl();
        List<TickerResutl> result = supportsList.stream().map(t -> {
            TickerResutl tickerResutl = new TickerResutl();
            tickerResutl.setBaseCoin(t.getSymbol());
            tickerResutl.setIcon(t.getIcourl());
            return tickerResutl;

        }).collect(Collectors.toList());

        Map symbolParams = new HashMap<>();
        symbolParams.put("symbols", symbols);
        String priceResult = client.createMarket().ticker(symbolParams);
        JSONArray currentPrices = JSON.parseArray(priceResult);
        currentPrices.stream().forEach(
                t -> {
                    JSONObject one = JSON.parseObject(t.toString());
                    TickerResutl rone = result.stream().filter(r -> r.getBaseCoin().equals(one.getString("symbol"))).findFirst().get();
                    rone.setPriceUsd(one.getFloat("lastPrice"));
                    rone.setOpen(one.getFloat("openPrice"));
                    rone.setClose(one.getFloat("lastPrice"));
                    rone.setVol(one.getFloat("volume"));
                }
        );

        symbolParams.put("symbols", symbols);
        String ticker24hResult = client.createMarket().ticker24H(symbolParams);
        JSONArray h24Prices = JSON.parseArray(ticker24hResult);
        h24Prices.stream().forEach(
                t -> {
                    JSONObject one = JSON.parseObject(t.toString());
                    TickerResutl rone = result.stream().filter(r -> r.getBaseCoin().equals(one.getString("symbol"))).findFirst().get();
                    rone.setChange24Hours(one.getFloat("priceChange"));
                }
        );
        redisUtil.set("QUETO",JSON.toJSON(result),1000);
        return result;
    }

    public ChargeAddress getChargeAddress(String id) {
        QueryWrapper<CoinKeys> queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id", id);
        queryWrapper.eq("env", "prod");
        queryWrapper.eq("exchange", "BINANCE");
        List<CoinKeys> res = iCoinKeysService.list(queryWrapper);

        String baseUrl = "";
        CoinKeys coinKeys = res.get(0);
        if (coinKeys.getEnv().equals("dev")) {

            baseUrl = "https://testnet.binance.vision";
        }

        if (coinKeys.getEnv().equals("prod")) {

            baseUrl = "https://api.binance.com";
        }
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
            ad.setUid(coinKeys.getUid());
            ad.setUrl(jsonObject.getString("url"));
            return ad;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;


    }

    private static double getUSDTBalance(JSONObject account) {
        JSONArray balances = account.getJSONArray("balances");

        for (Object o: balances) {
            JSONObject oj=(JSONObject) o;
            if (oj.getString("asset").equals("USDT")) {
                return oj.getDouble("free");
            }

        }
        return 0.0;
    }


}
