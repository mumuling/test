package com.zhongtie.work.util.parse;

import android.os.Bundle;

/**
 * 如果当前类不是Activity fragment 可实现OnParseBundleCallback方法获取一个Bundle
 *
 * @author: Chaek
 * @date: 2018/1/29
 */

public interface OnParseBundleCallback {
    /**
     * 获取数据源Bundle
     */
    Bundle getParseDataBundle();
}
