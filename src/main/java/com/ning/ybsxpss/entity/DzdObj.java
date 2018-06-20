package com.ning.ybsxpss.entity;

import java.util.List;

/**
 * Created by fxn on 2017/10/12.
 */

public class DzdObj {
    private String currentPaperNo;
    private String allRecordNums;
    private List<DzdList> list;
    private String paper_nums;

    public String getCurrentPaperNo() {
        return currentPaperNo;
    }

    public void setCurrentPaperNo(String currentPaperNo) {
        this.currentPaperNo = currentPaperNo;
    }

    public String getAllRecordNums() {
        return allRecordNums;
    }

    public void setAllRecordNums(String allRecordNums) {
        this.allRecordNums = allRecordNums;
    }

    public List<DzdList> getList() {
        return list;
    }

    public void setList(List<DzdList> list) {
        this.list = list;
    }

    public String getPaper_nums() {
        return paper_nums;
    }

    public void setPaper_nums(String paper_nums) {
        this.paper_nums = paper_nums;
    }
}
