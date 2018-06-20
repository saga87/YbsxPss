package com.ning.ybsxpss.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fxn on 2017/9/26.
 */

public class SourceGoodsListObj implements Serializable {
    private List<SourceGoodsList> list;
    private String orderId;

    public List<SourceGoodsList> getList() {
        return list;
    }

    public void setList(List<SourceGoodsList> list) {
        this.list = list;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
