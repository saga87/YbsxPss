package com.ning.ybsxpss.entity;

import java.util.List;

/**
 * Created by fxn on 2017/10/18.
 */

public class UserObj {
    private int currentPaperNo;
    private int allRecordNums;
    private List<UserList> list;
    private int paper_nums;

    public UserObj() {
    }

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

    public List<UserList> getList() {
        return list;
    }

    public void setList(List<UserList> list) {
        this.list = list;
    }

    public int getPaper_nums() {
        return paper_nums;
    }

    public void setPaper_nums(int paper_nums) {
        this.paper_nums = paper_nums;
    }
}
