package org.jeecg.modules.qe.enums;

public enum TradeEnum {
    WAIT_LOW_PRICE("9", "等待低位"),
    TADE("TADE", "交易"),
    SELL_ALL("SELL_ALL", "卖出所有订单"),
    TRADE_WAITTING("TRADE_WAITTING", "卖出所有订单"),
    TRADENO("PAUSE", "暂停交易");
    private String code;
    private String desc;
    TradeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }


}
