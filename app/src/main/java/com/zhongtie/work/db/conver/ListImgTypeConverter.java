package com.zhongtie.work.db.conver;

import com.alibaba.fastjson.JSON;
import com.raizlabs.android.dbflow.converter.TypeConverter;
import com.zhongtie.work.util.JsonUtil;
import com.zhongtie.work.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class ListImgTypeConverter extends TypeConverter<String, List<String>> {
    @Override
    public String getDBValue(List<String> model) {
        return JSON.toJSONString(model);
    }

    @Override
    public List<String> getModelValue(String data) {
        return JsonUtil.getPersons(data,String.class);
    }

}