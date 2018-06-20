package com.ning.ybsxpss.entity;

/**
 * Created by fxn on 2017/12/20.
 */

public class CheckReportObj {
    /**
     * id : 40b03188-f5ac-4e15-8c3b-f2851005161a
     * orderDetailId : a96a980c-2aa0-4eba-9744-70f0a826e81d
     * classificationId : 0202001
     * addtime : 2017-12-20 10:09:50
     * adduser : 19d0e3b3-71b4-4cf1-9ea9-96673151b6d0
     * check_type : 自检
     * check_item : 农残抑制率
     * check_standardVal : 50.0
     * check_testVal : 5.8
     * check_result : 合格
     * user_realname : 采购商一
     */

    private String id;
    private String orderDetailId;
    private String classificationId;
    private String addtime;
    private String adduser;
    private String check_type;
    private String check_item;
    private double check_standardVal;
    private double check_testVal;
    private String check_result;
    private String user_realname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public String getCheck_type() {
        return check_type;
    }

    public void setCheck_type(String check_type) {
        this.check_type = check_type;
    }

    public String getCheck_item() {
        return check_item;
    }

    public void setCheck_item(String check_item) {
        this.check_item = check_item;
    }

    public double getCheck_standardVal() {
        return check_standardVal;
    }

    public void setCheck_standardVal(double check_standardVal) {
        this.check_standardVal = check_standardVal;
    }

    public double getCheck_testVal() {
        return check_testVal;
    }

    public void setCheck_testVal(double check_testVal) {
        this.check_testVal = check_testVal;
    }

    public String getCheck_result() {
        return check_result;
    }

    public void setCheck_result(String check_result) {
        this.check_result = check_result;
    }

    public String getUser_realname() {
        return user_realname;
    }

    public void setUser_realname(String user_realname) {
        this.user_realname = user_realname;
    }
}
