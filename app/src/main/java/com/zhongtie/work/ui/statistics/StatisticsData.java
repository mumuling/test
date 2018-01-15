package com.zhongtie.work.ui.statistics;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.15
 */

public class StatisticsData {

    public List<String> names;
    public List<ValuesBean> values;

    public static class ValuesBean {
        /**
         * name : 第一季度
         * data : 0,0
         */

        public String name;
        public String data;
    }
}
