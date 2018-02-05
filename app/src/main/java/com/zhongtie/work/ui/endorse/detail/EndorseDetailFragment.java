package com.zhongtie.work.ui.endorse.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.endorse.create.EndorseCommonItemView;
import com.zhongtie.work.ui.safe.SafeSupervisionCreateActivity;
import com.zhongtie.work.ui.safe.dialog.OnSignatureListener;
import com.zhongtie.work.ui.safe.dialog.SignatureDialog;
import com.zhongtie.work.util.parse.BindKey;
import com.zhongtie.work.widget.SafeDividerItemDecoration;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ResourcesUtils;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class EndorseDetailFragment extends BasePresenterFragment<EndorseDetailContract.Presenter> implements
        EndorseDetailContract.View, OnSignatureListener {
    public static final String ID = "id";
    @BindKey(ID)
    private int mEndorssId;
    private View mHeadInfoView;
    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();

    private RecyclerView mList;
    private LinearLayout mBottom;

    private TextView mSign;
    private EditText mTitleEdit;


    public static void start(Context context, int endorssId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, endorssId);
        SafeSupervisionCreateActivity.newInstance(context, EndorseDetailFragment.class, context.getString(R.string.endorse_file_title), bundle);
    }

    public static void start(Context context) {
        start(context, 0);
    }


    @Override
    public int getLayoutViewId() {
        return R.layout.safe_order_info_fragment;
    }

    @Override
    public void initView() {
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mList = (RecyclerView) findViewById(R.id.list);
        LayoutInflater.from(getActivity()).inflate(R.layout.layout_endorse_detail_bottom, mBottom, true);

        mHeadInfoView = LayoutInflater.from(getContext()).inflate(R.layout.layout_file_endorse_head, mList, false);
        initAdapter();
        mTitleEdit = mHeadInfoView.findViewById(R.id.title_edit);
        mTitleEdit.setEnabled(false);
        mSign = (TextView) findViewById(R.id.sign);
        mSign.setOnClickListener(v -> endorseDialogShow());
    }

    /**
     * 签字dialog
     */
    private void endorseDialogShow() {
        new SignatureDialog(getActivity(), this).show();
    }


    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //基本界面
                .register(EndorseCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);
    }


    @Override
    protected void initData() {
        SafeDividerItemDecoration dividerItemDecoration = new SafeDividerItemDecoration(getContext(), VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ResourcesUtils.dip2px(10));
        dividerItemDecoration.setEndPosition(4);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
        mPresenter.getItemList(mEndorssId);
    }

    @Override
    protected EndorseDetailContract.Presenter getPresenter() {
        return new EndorseDetailPresenterImpl();
    }

    @Override
    public void setItemList(List<Object> itemList) {
        mCommonAdapter.setListData(itemList);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void setEndorseTitle(String title) {
        mTitleEdit.setText(title);
    }

    @Override
    public void onSignature(String imagePath) {
        mPresenter.endorseFile(imagePath);
    }

}
