package com.zhongtie.work.db.conver;

import com.raizlabs.android.dbflow.converter.TypeConverter;
import com.zhongtie.work.util.TimeUtils;

public class DateTypeConverter extends TypeConverter<String, Long> {


    @Override
    public String getDBValue(Long model) {
        return TimeUtils.formatYDateTime(model);
    }

    @Override
    public Long getModelValue(String data) {
        return TimeUtils.converter(data);
    }
}