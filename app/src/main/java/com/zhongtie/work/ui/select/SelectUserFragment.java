package com.zhongtie.work.ui.select;

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.CompanyTeamEntity;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.ui.base.BaseFragment;
import com.zhongtie.work.ui.safesupervision.item.CreateUserItemView;
import com.zhongtie.work.ui.select.item.SelectTeamItemView;
import com.zhongtie.work.ui.select.item.SelectTeamUserItemView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.zhongtie.work.ui.safesupervision.SafeSupervisionCreate2Fragment.imageUrls;

/**
 * 选择检查人，验证人，整改人，查阅组
 * Auth:Cheek
 * date:2018.1.11
 */

public class SelectUserFragment extends BaseFragment {
    private LinearLayout mBottom;
    private TextView mItemUserListTitle;
    private TextView mItemUserListTip;
    private ImageView mItemUserAddImg;
    private RecyclerView mCheckExamineList;
    private TextView mUpdateDownloadCancel;
    private TextView mUpdateBackGroundDownload;
    private AppCompatEditText mSearch;
    private RecyclerView mTempList;

    private CommonAdapter mSelectInfoAdapter;
    private List<CreateUserEntity> createUserEntities = new ArrayList<>();

    @Override
    public int getLayoutViewId() {
        return R.layout.select_user_fragment;
    }

    @Override
    public void initView() {
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mItemUserListTitle = (TextView) findViewById(R.id.item_user_list_title);
        mItemUserListTip = (TextView) findViewById(R.id.item_user_list_tip);
        mItemUserAddImg = (ImageView) findViewById(R.id.item_user_add_img);
        mCheckExamineList = (RecyclerView) findViewById(R.id.check_examine_list);
        mCheckExamineList.setLayoutManager(new LinearLayoutManager(getContext()));
        mUpdateDownloadCancel = (TextView) findViewById(R.id.update_download_cancel);
        mUpdateBackGroundDownload = (TextView) findViewById(R.id.update_back_ground_download);
        mSearch = (AppCompatEditText) findViewById(R.id.search);

        mTempList = (RecyclerView) findViewById(R.id.temp_list);
    }

    @Subscribe
    public void userEntityEvent(CreateUserEntity createUserEntity) {
        Iterator iterator = createUserEntities.iterator();
        while (iterator.hasNext()) {
            CreateUserEntity userEntity = (CreateUserEntity) iterator.next();
            if (userEntity.getUserId() == createUserEntity.getUserId()) {
                iterator.remove();
            }
        }
        createUserEntities.add(createUserEntity);
        mSelectInfoAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initData() {

        initTest();
        mItemUserListTitle.setText("已选成员");
        mItemUserListTip.setText("向右滑动查看更新");
        mSelectInfoAdapter = new CommonAdapter(createUserEntities);
        mCheckExamineList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        mSelectInfoAdapter.register(CreateUserItemView.class);
        mCheckExamineList.setAdapter(mSelectInfoAdapter);

    }

    private void initTest() {
        List<CompanyTeamEntity> createUserEntities = new ArrayList<>();

        for (int i = 0, len = 6; i < len; i++) {
            CompanyTeamEntity temp = new CompanyTeamEntity();
            temp.setTeamName("业务部" + i);

            List<CreateUserEntity> userList = new ArrayList<>();
            for (int j = 0, l = 6; j < l; j++) {
                userList.add(new CreateUserEntity("用户" + i, imageUrls[i], j * i));
            }
            temp.setTeamUserEntities(userList);
            createUserEntities.add(temp);
        }
        CommonAdapter list = new CommonAdapter(createUserEntities).register(SelectTeamItemView.class).register(SelectTeamUserItemView.class);
        mTempList.setAdapter(list);
    }
}
