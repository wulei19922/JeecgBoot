package org.jeecg.modules.qe.entity;


import lombok.Data;

@Data
public class TickerResutl {
    String icon;
    String baseCoin;
    float close;
    float vol;
    float open;
    float priceUsd;

    float change24Hours;



}
