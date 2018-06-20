package com.ning.ybsxpss.util;

/**
 * Created by fxn on 2017/6/6.
 */

public interface ICallBack {
    /**
     * 返回成功的数据
     */
    void succeed(Object object);
    /**
     *  返回失败的数据
     */
    void error(Object object);
}
