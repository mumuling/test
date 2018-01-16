package com.zhongtie.work.db.conver;

import com.raizlabs.android.dbflow.converter.TypeConverter;
import com.zhongtie.work.util.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStringTypeConverter extends TypeConverter<String, List<Integer>> {
    @Override
    public String getDBValue(List<Integer> model) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < model.size(); i++) {
            stringBuffer.append(model.get(i));
            stringBuffer.append(",");
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        return stringBuffer.toString();
    }

    @Override
    public List<Integer> getModelValue(String data) {
        List<Integer> integers = new ArrayList<>();
        String[] t = data.split(",");
        for (String aT : t) {
            if (TextUtil.isEmpty(aT)) {
                continue;
            }
            integers.add(Integer.valueOf(aT.trim()));
        }
        return integers;
    }

}