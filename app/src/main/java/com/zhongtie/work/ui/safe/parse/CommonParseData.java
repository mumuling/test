package com.zhongtie.work.ui.safe.parse;

import com.zhongtie.work.util.L;
import com.zhongtie.work.util.TextUtil;

import java.lang.reflect.Field;

/**
 * 自动解析Bundle参数和值
 *
 * @author: Chaek
 * @date: 2018/1/25
 */

public class CommonParseData {

    private Object model;
    private ParseDelegate parseData;

    public CommonParseData(Object object) {
        model = object;
        parseData = new FragmentParseDelegateImpl(object);
        initParse(object);
    }


    private void initParse(Object bean) {
        Class<?> cls = bean.getClass();
        // 取出bean里的所有方法
        Field[] fields = cls.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Parse parse = field.getAnnotation(Parse.class);
                // 获取属性的类型
                String type = field.getGenericType().toString();

                L.e("--------------------注解解析", type);
                L.e("--------------------注解解析", field.getDeclaringClass().toString());

                if (parse == null) {
                    continue;
                }

                //默认是当前变量名
                String key = parse.key();
                if (TextUtil.isEmpty(key)) {
                    key = field.getName();
                }

                Object value = null;
                if ("class java.lang.String".equals(type)) {
                    value = parseData.getString(key);
                } else if ("class java.lang.Integer".equals(type) || "int".equals(type)) {
                    value = parseData.getInt(key);
                } else if ("class java.lang.Boolean".equals(type) || "boolean".equals(type)) {
                    value = parseData.getBoolean(key);
                } else if ("class java.lang.Float".equals(type) || "float".equals(type)) {
                    value = parseData.getFloat(key);
                } else if ("class java.lang.Double".equals(type) || "double".equals(type)) {
                    value = parseData.getDouble(key);
                }


                field.set(bean, value);
                L.e("--------------------注解解析", field.getName());
                L.e("--------------------注解解析", "赋值" + value);
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
