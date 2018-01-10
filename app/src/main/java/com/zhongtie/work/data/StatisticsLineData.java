package com.zhongtie.work.data;

/**
 * Auth:Cheek
 * date:2018.1.10
 */

public class StatisticsLineData {
    private float percent;
    private String company;
    private int total;

    public StatisticsLineData(float percent, String company, int total) {
        this.percent = percent;
        this.company = company;
        this.total = total;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
