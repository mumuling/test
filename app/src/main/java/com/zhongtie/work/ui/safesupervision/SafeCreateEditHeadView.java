package com.zhongtie.work.ui.safesupervision;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.model.CompanyUnitEntity;
import com.zhongtie.work.ui.safesupervision.dialog.SelectDateTimeDialog;
import com.zhongtie.work.ui.select.CommonSelectSearchActivity;
import com.zhongtie.work.ui.select.ProjectTeamSelectFragment;

/**
 * 创建头部数据
 * Auth: Chaek
 * Date: 2018/1/11
 */

public class SafeCreateEditHeadView extends LinearLayout implements View.OnClickListener, SelectDateTimeDialog.OnSelectDateTimeListener {
    /**
     * 创建时间
     */
    private TextView mCreateTime;
    /**
     * 输入地址
     */
    private EditText mCreateAddress;

    private RelativeLayout mCreateCompanySelect;
    /**
     * 单位名称
     */
    private TextView mCreateCompany;
    private RelativeLayout mCreateCompanyWorkSelect;
    /**
     * 劳务公司
     */
    private TextView mCreateCompanyWork;


    private SelectDateTimeDialog mSelectDateTimeDialog;

    public SafeCreateEditHeadView(Context context) {
        this(context, null);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_safe_create_bast_info_title, this, true);
        mCreateTime = findViewById(R.id.create_time);
        mCreateAddress = findViewById(R.id.create_address);
        mCreateCompanySelect = findViewById(R.id.create_company_select);
        mCreateCompany = findViewById(R.id.create_company);
        mCreateCompanyWorkSelect = findViewById(R.id.create_company_work_select);
        mCreateCompanyWork = findViewById(R.id.create_company_work);
        findViewById(R.id.create_time_select).setOnClickListener(this);
        mCreateCompanySelect.setOnClickListener(this);
    }

    /**
     * @return 所属单位
     */
    public CompanyUnitEntity getCompanyUnitEntity() {
        //todo
        return null;
    }

    /**
     * @return 劳务关系公司
     */
    public CompanyUnitEntity getCompanyOfferEntity() {
        //todo
        return null;
    }

    /**
     * @return 获取选择的日期
     */
    public String getSelectDateTime() {
        return mCreateTime.getText().toString();
    }

    /**
     * @return 获取输入的地址
     */
    public String getInputAddress() {
        return mCreateAddress.getText().toString();
    }

    public SafeCreateEditHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_time_select:
                showSelectDateTime();
                break;
            case R.id.create_company_select:
                CommonSelectSearchActivity.newInstance(getContext(), ProjectTeamSelectFragment.class, "输入单位名称");
                break;
            case R.id.create_company_work_select:
                break;
            default:
        }
    }

    /**
     * 选择日期
     */
    private void showSelectDateTime() {
        if (mSelectDateTimeDialog == null) {
            SelectDateTimeDialog.Build build = new SelectDateTimeDialog.Build(getContext());
            build.setSelectDateTime(this);
            build.setSelectDateTime(new SelectDateTimeDialog.OnSelectDateTimeListener() {
                @Override
                public void setTimeDate(String datetime, int type) {
                }

                @Override
                public void setSelectType(int[] type, int buildType) {
                }
            });
            build.setType(SelectDateTimeDialog.BIRTH_DATE);
            mSelectDateTimeDialog = build.create();
        }
        mSelectDateTimeDialog.show();
    }

    @Override
    public void setTimeDate(String datetime, int type) {
        mCreateTime.setText(datetime);
    }

    @Override
    public void setSelectType(int[] type, int buildType) {

    }
}
