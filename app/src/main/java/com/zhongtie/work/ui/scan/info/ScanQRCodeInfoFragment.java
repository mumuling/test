package com.zhongtie.work.ui.scan.info;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.scan.OnNextFragmentListener;
import com.zhongtie.work.widget.BaseImageView;

import java.util.List;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class ScanQRCodeInfoFragment extends BasePresenterFragment<ScanQRCodeInfoContract.Presenter> implements ScanQRCodeInfoContract.View {

    private BaseImageView mUserInfoHead;
    private TextView mUserInfoName;
    private TextView mUserInfoCard;
    private ImageView mInfoTitleImg;
    private TextView mInfoTitleName;
    private TextView mInfoDuty;
    private TextView mInfoTypeWork;
    private TextView mInfoCompany;
    private TextView mInfoCompanyOffer;
    private TextView mInfoStudyDate;
    private TextView mInfoHealthState;
    private TextView mInfoWorkState;
    private TextView mInfoInsurance;
    private TextView mItemContent;
    private ImageView mImg1;
    private TextView mInfoErrTitle;
    private Button mAddError;
    private TextView mInfoErrorList;

    private TextView mInfoViolateTitle;
    private Button mAddViolate;

    private OnNextFragmentListener onNextFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNextFragmentListener) {
            onNextFragmentListener = (OnNextFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(onNextFragmentListener!=null)
        {
            onNextFragmentListener.onResumeCamera();
        }
    }

    @Override
    public void setInfoList(List<Object> objectList) {

    }

    @Override
    protected ScanQRCodeInfoContract.Presenter getPresenter() {
        return new ScanInfoPresenterImpl();
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.qr_user_info_fragment;
    }

    @Override
    public void initView() {

        mUserInfoHead = (BaseImageView) findViewById(R.id.user_info_head);
        mUserInfoName = (TextView) findViewById(R.id.user_info_name);
        mUserInfoCard = (TextView) findViewById(R.id.user_info_card);
        mInfoTitleImg = (ImageView) findViewById(R.id.info_title_img);
        mInfoTitleName = (TextView) findViewById(R.id.info_title_name);
        mInfoDuty = (TextView) findViewById(R.id.info_duty);
        mInfoTypeWork = (TextView) findViewById(R.id.info_type_work);
        mInfoCompany = (TextView) findViewById(R.id.info_company);
        mInfoCompanyOffer = (TextView) findViewById(R.id.info_company_offer);
        mInfoStudyDate = (TextView) findViewById(R.id.info_study_date);
        mInfoHealthState = (TextView) findViewById(R.id.info_health_state);
        mInfoWorkState = (TextView) findViewById(R.id.info_work_state);
        mInfoInsurance = (TextView) findViewById(R.id.info_insurance);
        mItemContent = (TextView) findViewById(R.id.item_content);
        mImg1 = (ImageView) findViewById(R.id.img1);
//        mInfoErrTitle = (TextView) findViewById(R.id.info_err_title);

        mInfoViolateTitle = (TextView) findViewById(R.id.info_violate_title);
        mAddViolate = (Button) findViewById(R.id.add_violate);
        mAddViolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddViolateDialog(getActivity()).show();
            }
        });

    }

    @Override
    protected void initData() {
//        initLoading();
    }
}
