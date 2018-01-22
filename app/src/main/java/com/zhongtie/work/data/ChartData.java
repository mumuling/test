package com.zhongtie.work.data;

import android.support.annotation.Keep;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.15
 */
@Keep
public class ChartData {


    /**
     * year : 2017
     * part_1 : {"all":["0","0","0","0","0","0","0","0"]}
     * part_2 : {"all":["0","0","0","0","0","0","1","0"]}
     * part_3 : {"all":["3","2","1","3","2","1","1","1"]}
     * part_4 : {"all":["0","0","0","1","0","0","0","0"]}
     * part_5 : {"all":["3","2","1","4","2","1","2","1"]}
     */

    public String year;
    public PartBean part_1;
    public PartBean part_2;
    public PartBean part_3;
    public PartBean part_4;
    public PartBean part_5;

    public static class PartBean {
        public List<String> all;

        public List<String> getAll() {
            return all;
        }

        public void setAll(List<String> all) {
            this.all = all;
        }
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public PartBean getPart_1() {
        return part_1;
    }

    public void setPart_1(PartBean part_1) {
        this.part_1 = part_1;
    }

    public PartBean getPart_2() {
        return part_2;
    }

    public void setPart_2(PartBean part_2) {
        this.part_2 = part_2;
    }

    public PartBean getPart_3() {
        return part_3;
    }

    public void setPart_3(PartBean part_3) {
        this.part_3 = part_3;
    }

    public PartBean getPart_4() {
        return part_4;
    }

    public void setPart_4(PartBean part_4) {
        this.part_4 = part_4;
    }

    public PartBean getPart_5() {
        return part_5;
    }

    public void setPart_5(PartBean part_5) {
        this.part_5 = part_5;
    }

    public PartBean getPart(int position) {
        switch (position) {
            case 0:
                return part_1;
            case 1:
                return part_2;
            case 2:
                return part_3;
            case 3:
                return part_4;
            case 4:
                return part_5;
        }
        return part_5;
    }
}
