package cn.zhang.ai.api.domain.zsxq.model.aggregates;

import cn.zhang.ai.api.domain.zsxq.model.res.RespData;

/**
 * 回答问题的聚合信息
 */
public class UnAnsweredQuestionAggregates {

    private  boolean succeeded;
    private RespData resp_Data;

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public void setResp_Data(RespData resp_Data) {
        this.resp_Data = resp_Data;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public RespData getResp_Data() {
        return resp_Data;
    }
}
