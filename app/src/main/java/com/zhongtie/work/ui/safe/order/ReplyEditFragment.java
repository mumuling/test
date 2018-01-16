package com.zhongtie.work.ui.safe.order;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.UploadApi;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.image.MultiImageSelector;
import com.zhongtie.work.ui.image.MultiImageSelectorActivity;
import com.zhongtie.work.ui.safe.dialog.OnSignatureListener;
import com.zhongtie.work.ui.safe.dialog.SignatureDialog;
import com.zhongtie.work.ui.safe.item.CreatePicItemView;
import com.zhongtie.work.util.ToastUtil;
import com.zhongtie.work.widget.AdapterDataObserver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.image.MultiImageSelector.REQUEST_CODE;

/**
 * Auth:Cheek
 * date:2018.1.12
 */

public class ReplyEditFragment extends BaseFragment implements OnSignatureListener, AdapterDataObserver.OnAdapterDataChangedListener {

    private TextView mEditTitle;
    private EditText mCreateModifyContent;
    private TextView mItemUserListTitle;
    private TextView mItemUserListTip;
    private ImageView mItemUserAddImg;
    private RecyclerView mCheckExamineList;
    private TextView mSubmit;

    private CommonAdapter commonAdapter;
    private List<String> mPicList = new ArrayList<>();

    @Override
    public int getLayoutViewId() {
        return R.layout.reply_edit_fragment;
    }

    @Override
    public void initView() {

        mEditTitle = (TextView) findViewById(R.id.edit_title);
        mCreateModifyContent = (EditText) findViewById(R.id.create_modify_content);
        mItemUserListTitle = (TextView) findViewById(R.id.item_user_list_title);
        mItemUserListTip = (TextView) findViewById(R.id.item_user_list_tip);
        mItemUserAddImg = (ImageView) findViewById(R.id.item_user_add_img);
        mCheckExamineList = (RecyclerView) findViewById(R.id.check_examine_list);
        mSubmit = (TextView) findViewById(R.id.submit);

        mItemUserListTitle.setText(R.string.imaage_title);
        mEditTitle.setText(R.string.description);
        mItemUserListTip.setText(R.string.select_photo_tip);
        mCreateModifyContent.setHint(R.string.description_hint);

        mItemUserAddImg.setImageResource(R.drawable.ic_cam);
        mItemUserAddImg.setVisibility(View.VISIBLE);
        mItemUserAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = MultiImageSelector.MAX_COUNT - mPicList.size();
                MultiImageSelector.create().count(count).start(ReplyEditFragment.this, REQUEST_CODE);
            }
        });
        mCheckExamineList.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.HORIZONTAL, false));

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SignatureDialog(getActivity(), ReplyEditFragment.this).show();
            }
        });
    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter(mPicList).register(new CreatePicItemView(false));
        mCheckExamineList.setAdapter(commonAdapter);
        commonAdapter.registerAdapterDataObserver(new AdapterDataObserver(this));
        onChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == MultiImageSelector.REQUEST_CODE) {
                List<String> selectImgList = data.getStringArrayListExtra(MultiImageSelectorActivity.BASE_RESULT);
                addPicList(selectImgList);

            }
        }
    }

    private void addPicList(List<String> selectImgList) {
        for (int i = 0; i < selectImgList.size(); i++) {
            if (!mPicList.contains(selectImgList.get(i))) {
                mPicList.add(selectImgList.get(i));
            }
        }
        commonAdapter.setListData(mPicList);
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSignature(String imagePath) {

        Http.netServer(UploadApi.class)
                .uploadPic(Cache.getUserID(),imagePath)
                .compose(Network.networkConvertDialog(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastUtil.showToast(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }

    @Override
    public void onChanged() {
        if (mPicList.isEmpty()) {
            mCheckExamineList.setVisibility(View.GONE);
        } else {
            mCheckExamineList.setVisibility(View.VISIBLE);
        }
    }
}
