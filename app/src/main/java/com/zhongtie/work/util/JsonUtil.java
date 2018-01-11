package com.zhongtie.work.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;


public class JsonUtil {
    /**
     * 解析mode String JSON
     *
     * @param jsonString 原始数据
     * @param cls        Mode类型
     * @return Mode
     */
    public static <T> T getPerson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * 解析json list
     *
     * @param <T>
     * @param jsonString 原始json格式数据
     * @param cls        mode类型
     * @return list数组
     */
    public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
        }
        return list;
    }
}
