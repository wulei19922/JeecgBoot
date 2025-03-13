package org.jeecg.modules.wechat.service;

import com.alibaba.fastjson.JSONObject;

public interface VouchersService {




    /* 激活代金券*/
    public JSONObject  create(JSONObject params);


    /* 激活代金券*/
    public JSONObject  startStock(JSONObject params);




    /* 暂停*/
    public JSONObject  stopStock(JSONObject params);



    /*重启*/
    public JSONObject  restartStock(JSONObject params);


    /*查询*/
    public JSONObject  listStock(JSONObject params);

    /*查询核销批次*/
    public JSONObject  useFlow(JSONObject params);




}
