package com.zhongtie.work.ui.safe.detail;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhongtie.work.R;
import com.zhongtie.work.app.Cache;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.event.ReplyEvent;
import com.zhongtie.work.network.Http;
import com.zhongtie.work.network.Network;
import com.zhongtie.work.network.api.SafeApi;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.image.MultiImageSelector;
import com.zhongtie.work.ui.image.MultiImageSelectorActivity;
import com.zhongtie.work.ui.safe.dialog.OnSignatureListener;
import com.zhongtie.work.ui.safe.dialog.SignatureDialog;
import com.zhongtie.work.ui.safe.item.CreatePicItemView;
import com.zhongtie.work.util.TextUtil;
import com.zhongtie.work.util.upload.UploadUtil;
import com.zhongtie.work.widget.AdapterDataObserver;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zhongtie.work.ui.image.MultiImageSelector.REQUEST_CODE;

/**
 * 安全督导回复Fragment
 * date:2018.1.12
 *
 * @author Chaek
 */

public class ReplyEditFragment extends BaseFragment implements OnSignatureListener, AdapterDataObserver.OnAdapterDataChangedListener {

    public static final String EVENT_ID = "event_id";
    private int mSafeEventId;
    private EditText mCreateModifyContent;
    private RecyclerView mCheckExamineList;

    private CommonAdapter commonAdapter;
    private List<String> mPicList = new ArrayList<>();

    @Override
    public int getLayoutViewId() {
        mSafeEventId = getArguments().getInt(EVENT_ID, 0);
        return R.layout.reply_edit_fragment;
    }

    @Override
    public void initView() {

        TextView editTitle = (TextView) findViewById(R.id.edit_title);
        mCreateModifyContent = (EditText) findViewById(R.id.create_modify_content);
        TextView itemUserListTitle = (TextView) findViewById(R.id.item_user_list_title);
        TextView itemUserListTip = (TextView) findViewById(R.id.item_user_list_tip);
        ImageView itemUserAddImg = (ImageView) findViewById(R.id.item_user_add_img);
        mCheckExamineList = (RecyclerView) findViewById(R.id.check_examine_list);
        TextView submit = (TextView) findViewById(R.id.submit);

        itemUserListTitle.setText(R.string.imaage_title);
        editTitle.setText(R.string.description);
        itemUserListTip.setText(R.string.select_photo_tip);
        mCreateModifyContent.setHint(R.string.description_hint);

        itemUserAddImg.setImageResource(R.drawable.ic_cam);
        itemUserAddImg.setVisibility(View.VISIBLE);
        itemUserAddImg.setOnClickListener(view -> {
            int count = MultiImageSelector.MAX_COUNT - mPicList.size();
            MultiImageSelector.create().count(count).start(ReplyEditFragment.this, REQUEST_CODE);
        });
        mCheckExamineList.setLayoutManager(new LinearLayoutManager(getAppContext(), LinearLayoutManager.HORIZONTAL, false));

        submit.setOnClickListener(view -> replyEvent());
    }

    private void replyEvent() {
        String content = mCreateModifyContent.getText().toString();
        if (TextUtil.isEmpty(content)) {
            showToast(getString(R.string.input_reply_content));
            return;
        }
        new SignatureDialog(getActivity(), ReplyEditFragment.this).show();
    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter(mPicList).register(new CreatePicItemView(true));
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
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSignature(String imagePath) {
        ArrayMap<String, Object> postDataList = new ArrayMap<>();
        postDataList.put("event_detail", mCreateModifyContent.getText().toString());
        postDataList.put("event_userid", Cache.getUserID());
        postDataList.put("id", mSafeEventId);
        UploadUtil.uploadSignPNG(imagePath)
                .flatMap(uploadData -> {
                    postDataList.put("event_sign", uploadData.getPicname());
                    return UploadUtil.uploadListJPGIDList(mPicList);
                })
                .flatMap(picList -> {
                    postDataList.put("event_pic", picList);
                    return Http.netServer(SafeApi.class).replyEvent(postDataList);
                }).compose(Network.convertDialogTip(ReplyEditFragment.this))
                .subscribe(integer -> {
                    showToast(getString(R.string.reply_success));
                    new ReplyEvent().post();
                    getActivity().finish();
                }, throwable -> {

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

    @Override
    public boolean isFetchBackEvent() {
        return true;
    }

    @Override
    public void onClickBack() {
        super.onClickBack();
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_tip)
                .content(R.string.dialog_edit_exit_tip)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    getActivity().finish();
                }).onNegative((dialog, which) -> dialog.dismiss())
                .build().show();
    }
}
