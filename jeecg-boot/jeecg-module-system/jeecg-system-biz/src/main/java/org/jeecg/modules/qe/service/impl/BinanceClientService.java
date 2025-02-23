package org.jeecg.modules.qe.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.binance.connector.client.impl.SpotClientImpl;
import org.jeecg.modules.qe.entity.CoinSupport;
import org.jeecg.modules.qe.entity.TickerResutl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class BinanceClientService {

    @Autowired
    CoinSupportServiceImpl coinSupportService;


    public  List<TickerResutl> getList(boolean isUp) {
        QueryWrapper<CoinSupport>queryWrapper=new QueryWrapper<>();
        if (isUp){
            queryWrapper.eq("up","Y");
        }

        List<CoinSupport> supportsList = coinSupportService.list(queryWrapper);
        ArrayList symbols=new ArrayList();

        supportsList.stream().forEach(t->{

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

        Map symbolParams=new HashMap<>();
        symbolParams.put("symbols",symbols);
        String priceResult = client.createMarket().ticker(symbolParams);
        JSONArray currentPrices = JSON.parseArray(priceResult);
        currentPrices.stream().forEach(
                t->{
                    JSONObject one = JSON.parseObject(t.toString());
                    TickerResutl rone=result.stream().filter(r->r.getBaseCoin().equals(one.getString("symbol"))).findFirst().get();
                    rone.setPriceUsd(one.getFloat("lastPrice"));
                    rone.setOpen(one.getFloat("openPrice"));
                    rone.setClose(one.getFloat("lastPrice"));
                    rone.setVol(one.getFloat("volume"));
                }
        );

        symbolParams.put("symbols",symbols);
        String ticker24hResult = client.createMarket().ticker24H(symbolParams);
        JSONArray h24Prices = JSON.parseArray(ticker24hResult);
        h24Prices.stream().forEach(
                t->{
                    JSONObject one = JSON.parseObject(t.toString());
                    TickerResutl rone=result.stream().filter(r->r.getBaseCoin().equals(one.getString("symbol"))).findFirst().get();
                    rone.setChange24Hours(one.getFloat("priceChange"));
                }
        );


        return  result;
    }
}
