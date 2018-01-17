package com.zhongtie.work.ui.setting;

import android.widget.CheckBox;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.util.SharePrefUtil;

/**
 * 通知设置
 * date:2018.1.10
 *
 * @author Chaek
 */

public class NoticeSettingFragment extends BaseFragment {
    public static final String IS_NOTICE = "is_notice";
    private CheckBox mNoticeCheck;
    private TextView mModifyPassword;

    @Override
    public int getLayoutViewId() {
        return R.layout.fragment_notice_setting;
    }

    @Override
    public void initView() {
        mNoticeCheck = (CheckBox) findViewById(R.id.notice_check);
        mModifyPassword = (TextView) findViewById(R.id.modify_password);
    }

    @Override
    protected void initData() {
        boolean isCheckNotice = SharePrefUtil.getInstance().getBoolean("notice", true);
        mNoticeCheck.setChecked(isCheckNotice);
        mModifyPassword.setOnClickListener(v -> {
            SharePrefUtil.getInstance().getBoolean(IS_NOTICE, mNoticeCheck.isChecked());
            showToast(R.string.notice_setting);
            getActivity().finish();
        });
    }
}
