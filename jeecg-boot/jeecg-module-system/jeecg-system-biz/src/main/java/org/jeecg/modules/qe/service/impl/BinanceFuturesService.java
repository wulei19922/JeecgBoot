package org.jeecg.modules.qe.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.binance.connector.client.SpotClient;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.um_futures.UMUserData;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.qe.entity.*;
import org.jeecg.modules.qe.service.ICoinBotFutureService;
import org.jeecg.modules.qe.service.ICoinBotService;
import org.jeecg.modules.qe.service.ICoinKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BinanceFuturesService {

    @Autowired
    CoinSupportServiceImpl coinSupportService;

    @Autowired
    ICoinKeysService iCoinKeysService;

    @Autowired
    ICoinBotService botService;


    @Autowired
    ICoinBotFutureService coinBotFutureService;


    @Autowired
    RedisUtil redisUtil;



    public JSONArray getAssetInfo(String userId) {
        //获得机机器人symbol
        QueryWrapper<CoinKeys> queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id", userId);
        queryWrapper.eq("env", "prod");
        queryWrapper.eq("exchange", "BINANCE");
        CoinKeys  key = iCoinKeysService.list(queryWrapper).stream().findFirst().orElse(null);
        String apiKey = key.getApiKey();
        String secretKey = key.getApiSecret();
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        UMFuturesClientImpl client = new UMFuturesClientImpl(apiKey, secretKey);

        String  umUserData = client.account().futuresAccountBalance(parameters);
        JSONArray jsonObject = JSON.parseArray(umUserData);
        return  jsonObject;
    }
    /**
     * 查询用户合约持仓
     * @param id
     * @return
     */
    public JSONArray getFuturesPostions(String botId) {

        //获得机机器人symbol
        CoinBot coinBot = botService.getById(botId);
        String symbol = coinBot.getSymbol();

        QueryWrapper<CoinKeys> queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id", coinBot.getMemberId());
        queryWrapper.eq("env", "prod");
        queryWrapper.eq("exchange", "BINANCE");
        CoinKeys  key = iCoinKeysService.list(queryWrapper).stream().findFirst().orElse(null);
        if (key==null){
            return null;
        }
        String baseUrl = "https://api.binance.com";
        // 替换为你的API密钥和私钥
        String apiKey = key.getApiKey();
        String secretKey = key.getApiSecret();
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        UMFuturesClientImpl client = new UMFuturesClientImpl(apiKey, secretKey);
        parameters.put("symbol", symbol);
        try {
            String result = client.account().positionInformation(parameters);
            JSONArray arr= JSONArray.parseArray(result);
            return  arr;
        } catch (BinanceConnectorException e) {
            e.printStackTrace();
        } catch (BinanceClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询用户合约持仓
     * @param id
     * @return
     */
    public List<CoinBotFuture> getFuturesOpenOrders(String botId) {

        //获得机机器人symbol
        CoinBot coinBot = botService.getById(botId);
        String symbol = coinBot.getSymbol();
        QueryWrapper<CoinKeys> queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id", coinBot.getMemberId());
        queryWrapper.eq("env", "prod");
        queryWrapper.eq("exchange", "BINANCE");
        CoinKeys  key = iCoinKeysService.list(queryWrapper).stream().findFirst().orElse(null);
        if (key==null){
            return null;
        }
        //查询当前委托信息
        QueryWrapper<CoinBotFuture> futureQueryWrapper = new QueryWrapper();
        futureQueryWrapper.eq("bot_id",botId);
        futureQueryWrapper.eq("order_status","NEW");
        List<CoinBotFuture> list = coinBotFutureService.list(futureQueryWrapper);
        String apiKey = key.getApiKey();
        String secretKey = key.getApiSecret();
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        UMFuturesClientImpl client = new UMFuturesClientImpl(apiKey, secretKey);

        parameters.put("symbol", symbol);
        if (list.size()>0){
            for (CoinBotFuture coinBotFuture : list) {
                String orderId = coinBotFuture.getOrderId();
                parameters.put("orderId", orderId);
                try {
                    String result = client.account().queryOrder(parameters);
                    JSONObject jsonObject1 = JSONObject.parseObject(result);
                    String status = jsonObject1.getString("status");
                    coinBotFuture.setOrderStatus(status);
                }catch (Exception e ){
                    log.error(e.getMessage());
                }
            }
        }
        return list;
    }



    //创建限价订单
    public CoinBotFuture createOrder(CoinBotFuture jsonObject) {

        //获得机机器人symbol
        CoinBot coinBot = botService.getById(jsonObject.getBotId());
        String symbol = coinBot.getSymbol();

        QueryWrapper<CoinKeys> queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id", coinBot.getMemberId());
        queryWrapper.eq("env", "prod");
        queryWrapper.eq("exchange", "BINANCE");
        CoinKeys  key = iCoinKeysService.list(queryWrapper).stream().findFirst().orElse(null);
        if (key==null){
            return null;
        }
        String baseUrl = "https://api.binance.com";
        // 替换为你的API密钥和私钥
        String apiKey = key.getApiKey();
        String secretKey = key.getApiSecret();
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        UMFuturesClientImpl client = new UMFuturesClientImpl(apiKey, secretKey);

        String silde = jsonObject.getSilde();
        //1 开多
        //2 平多
        //3 开空
        //4 平空
        if (silde.equals("1")){
            parameters.put("side", "BUY");
            parameters.put("positionSide", "LONG");//双向持仓模式 多单

        }

        if (silde.equals("2")){
            parameters.put("side", "SELL");
            parameters.put("positionSide", "LONG");//双向持仓模式 多单

        }


        if (silde.equals("3")){
            parameters.put("side", "SELL");
            parameters.put("positionSide", "SHORT");//双向持仓模式 多单

        }

        if (silde.equals("4")){
            parameters.put("side", "BUY");
            parameters.put("positionSide", "SHORT");//双向持仓模式 多单

        }
        parameters.put("symbol", symbol);

        parameters.put("type", "LIMIT");//限价订单
        parameters.put("quantity", 0.002);//数量
        parameters.put("price", 84000.00);//委托价格
        parameters.put("newClientOrderId", "BINANCE_12300");//客户端ID
        parameters.put("timeInForce", "GTC");//客户端ID


        try {
            String result = client.account().newOrder(parameters);
            JSONObject arr= JSONArray.parseObject(result);
            jsonObject.setOrderId(arr.getString("orderId"));
            return  jsonObject;
        } catch (BinanceConnectorException e) {
            e.printStackTrace();
        } catch (BinanceClientException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
