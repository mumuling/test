package com.zhongtie.work.ui.safe.parse;

import android.os.Parcelable;

import com.zhongtie.work.util.L;
import com.zhongtie.work.util.TextUtil;

import java.io.Serializable;
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
                Parse parse = field.getAnnotation(Parse.class);
                // 获取属性的类型
                String type = field.getGenericType().toString().replace("class ", "");

                L.e("--------------------注解解析", type);
                if (parse == null) {
                    continue;
                }
                field.setAccessible(true);
                //默认是当前变量名
                String key = parse.key();
                if (TextUtil.isEmpty(key)) {
                    key = field.getName();
                }
                //                class ;
                Object value = null;
                switch (type) {
                    case "java.lang.String":
                        value = parseData.getString(key);
                        break;
                    case "[Ljava.lang.String;":
                        value = parseData.getStringArray(key);
                        break;
                    case "java.lang.Integer":
                    case "int":
                        value = parseData.getInt(key);
                        break;
                    case "java.lang.Boolean":
                    case "boolean":
                        value = parseData.getBoolean(key);
                        break;
                    case "java.lang.Float":
                    case "float":
                        value = parseData.getFloat(key);
                        break;
                    case "java.lang.Double":
                    case "double":
                        value = parseData.getDouble(key);
                        break;
                    default:
                        Class<?> clazz = null;
                        try {
                            clazz = Class.forName(type);
                            boolean isSerializable = Serializable.class.isAssignableFrom(clazz);
                            if (isSerializable) {
                                value = parseData.getSerializable(key);
                            } else {
                                boolean isParcelable = Parcelable.class.isAssignableFrom(clazz);
                                if (isParcelable) {
                                    value = parseData.getParcelable(key);
                                }
                            }
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }

                }
                field.set(bean, value);
                L.e("--------------------注解解析", field.getName());
                L.e("--------------------注解解析", "赋值" + value);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
