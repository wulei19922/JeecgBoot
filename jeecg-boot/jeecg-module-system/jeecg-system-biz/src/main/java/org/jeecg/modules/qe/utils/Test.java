package org.jeecg.modules.qe.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.futures.client.impl.CMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.um_futures.UMUserData;
import io.lettuce.core.ScriptOutputType;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class Test {


    public static void main(String[] args) {
        String key="5tiDiv3NI0U107lFfBdenGZNkdNElakAdFuCd3mQGW3S9IbI7gQNHLLLMFEfwy9h";
        String secret="TVPiuFyWkundIXJrXbce4TXwkhDWmoMm0xBe124ETr0rP8NK7yCrYL9ZqHnEK6Nn";

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        UMFuturesClientImpl client = new UMFuturesClientImpl(key, secret);
        parameters.put("symbol", "BTCUSDT");
        parameters.put("side", "SELL");
        parameters.put("positionSide", "LONG");//双向持仓模式 多单
        parameters.put("type", "LIMIT");//限价订单
        parameters.put("quantity", 0.001);//限价订单
        parameters.put("price", 87000.00);//委托价格
        parameters.put("newClientOrderId", "BINANCE_12300");//客户端ID
        parameters.put("timeInForce", "GTC");//客户端ID
//        parameters.put("stopPrice", 95000.00);//止损价格



        try {

            String s = client.market().exchangeInfo();

            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONArray symbols = jsonObject.getJSONArray("symbols");
            JSONObject symbolInfo = (JSONObject)symbols.stream().filter(o -> {
                JSONObject t = (JSONObject) o;
                String symbol = t.getString("symbol");
                return symbol.equals("BTCUSDT");
            }).findFirst().orElse(null);
            BigDecimal pricePrecision = symbolInfo.getBigDecimal("pricePrecision");
            BigDecimal quantityPrecision = symbolInfo.getBigDecimal("quantityPrecision");


            System.out.println(pricePrecision);
            System.out.println(quantityPrecision);
            String result = client.account().newOrder(parameters);
//            JSONObject r=(JSONObject)symbolInfo.getJSONArray("filters").stream().filter(o -> {
//                JSONObject t = (JSONObject) o;
//                return t.getString("filterType").equals("LOT_SIZE");
//            }).findFirst().orElse(null);
//            BigDecimal stepSize = r.getBigDecimal("stepSize");

//            System.out.println(stepSize);

        } catch (BinanceConnectorException e) {
            e.printStackTrace();
        } catch (BinanceClientException e) {
            e.printStackTrace();
        }



    }
}
