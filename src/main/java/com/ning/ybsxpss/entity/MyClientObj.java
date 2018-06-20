package com.ning.ybsxpss.entity;

import java.util.List;

/**
 * Created by fxn on 2017/10/12.
 */

public class MyClientObj {
    private int currentPaperNo;
    private int allRecordNums;
    private List<MyClientList> list;
    private int paper_nums;

    public int getCurrentPaperNo() {
        return currentPaperNo;
    }

    public void setCurrentPaperNo(int currentPaperNo) {
        this.currentPaperNo = currentPaperNo;
    }

    public int getAllRecordNums() {
        return allRecordNums;
    }

    public void setAllRecordNums(int allRecordNums) {
        this.allRecordNums = allRecordNums;
    }

    public List<MyClientList> getList() {
        return list;
    }

    public void setList(List<MyClientList> list) {
        this.list = list;
    }

    public int getPaper_nums() {
        return paper_nums;
    }

    public void setPaper_nums(int paper_nums) {
        this.paper_nums = paper_nums;
    }
}
