package com.zhongtie.work.data;

import android.support.annotation.Keep;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.15
 */
@Keep
public class StatisticsData {

    public List<String> names;
    public List<ValuesBean> values;

    @Keep
    public static class ValuesBean {
        /**
         * name : 第一季度
         * data : 0,0
         */

        public String name;
        public String data;
    }
}
