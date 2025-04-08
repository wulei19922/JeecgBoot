package org.jeecg.modules.qe.enums;

public enum CoinBotEnum {

    WAIT_START("0", "待启动"),
    NO_RUNNING("1", "未运行"),
    RUNNING("2", "运行中"),
    EXCEPTION("3", "异常"),
    STOP_TRADE("4", "暂停交易"),
    WAIT_SETTLEMENT("5", "待结算"),
    SETTLEMENTING("6", "结算中"),
    SEETLEMENTED("7", "已结算"),
    WAIT_RESTART("8", "待重启"),
    WAIT_LOW_PRICE("9", "等待低位");
    private String code;
    private String desc;
    CoinBotEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }


}
