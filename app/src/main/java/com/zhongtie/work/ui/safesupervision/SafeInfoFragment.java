package com.zhongtie.work.ui.safesupervision;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.zhongtie.work.R;
import com.zhongtie.work.base.adapter.CommonAdapter;
import com.zhongtie.work.data.create.CommonListTypeItem;
import com.zhongtie.work.data.create.CreateCategoryData;
import com.zhongtie.work.data.create.CreateTypeItem;
import com.zhongtie.work.data.create.CreateUserEntity;
import com.zhongtie.work.data.create.EditContentEntity;
import com.zhongtie.work.model.SafeSupervisionEnity;
import com.zhongtie.work.ui.base.BasePresenterFragment;
import com.zhongtie.work.ui.safesupervision.item.CreateCommonItemView;
import com.zhongtie.work.ui.safesupervision.item.CreateEditContentItemView;
import com.zhongtie.work.ui.safesupervision.item.CreateSelectTypeItemView;
import com.zhongtie.work.util.Util;
import com.zhongtie.work.util.ViewUtils;
import com.zhongtie.work.widget.DividerItemDecoration;
import com.zhongtie.work.widget.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.zhongtie.work.widget.DividerItemDecoration.VERTICAL_LIST;

/**
 * Auth:Cheek
 * date:2018.1.9
 */

public class SafeInfoFragment extends BasePresenterFragment<SafeSupervisionContract.Presenter> implements SafeSupervisionContract.View, RefreshRecyclerView.RefreshPageConfig {

    public static final String ID = "id";
    private int mSafeID;


    private View mHeadInfoView;
    private RecyclerView mList;

    private CommonAdapter mCommonAdapter;
    private List<Object> mInfoList = new ArrayList<>();


    public static SafeInfoFragment newInstance(int id) {
        Bundle args = new Bundle();
        SafeInfoFragment fragment = new SafeInfoFragment();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutViewId() {
        return R.layout.base_recyclerview;
    }

    @Override
    public void initView() {
        mList = (RecyclerView) findViewById(R.id.list);

//        mSafeID = getArguments().getInt(ID);
        mHeadInfoView = new SafeCreateEditHeadView(getActivity());
        initAdapter();

    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter(mInfoList)
                //选择类别
                .register(CreateSelectTypeItemView.class)
                //输入数据
                .register(CreateEditContentItemView.class)
                //基本界面
                .register(CreateCommonItemView.class);
        mCommonAdapter.addHeaderView(mHeadInfoView);

        View mFooterView = LayoutInflater.from(getAppContext()).inflate(R.layout.layout_modify_pw_bottom, mList, false);
        mCommonAdapter.addFooterView(mFooterView);
        initTest();
    }

    private void initTest() {
        String[] typeList = getResources().getStringArray(R.array.type_list);
        CreateTypeItem createTypeItem = new CreateTypeItem("问题类型");
        List<CreateCategoryData> createQuestionTypes = new ArrayList<>();
        for (int i = 0, len = typeList.length; i < len; i++) {
            createQuestionTypes.add(new CreateCategoryData(typeList[i], i, false));
        }
        createTypeItem.setTypeItemList(createQuestionTypes);
        mInfoList.add(createTypeItem);
        mInfoList.add(new EditContentEntity("描述", "请输入事件描述", ""));
        mInfoList.add(new EditContentEntity("整改要求", "请输入整改要求", ""));

        //图片
        CommonListTypeItem<String> picData = new CommonListTypeItem<String>("上传照片", "最多12张", R.drawable.ic_cam, true);
        mInfoList.add(picData);
        List<String> picList = new ArrayList<>();
        for (int i = 0, len = typeList.length; i < len; i++) {
            picList.add(imageUrls[i]);
        }
        picData.setTypeItemList(picList);

        List<CreateUserEntity> userList = new ArrayList<>();
        for (int i = 0, len = typeList.length; i < len; i++) {
            userList.add(new CreateUserEntity("用户" + i, imageUrls[i], i));
        }

        CommonListTypeItem<CreateUserEntity> check = new CommonListTypeItem<CreateUserEntity>("检查人", "向右滑动查看更多", R.drawable.plus, true);
        mInfoList.add(check);
        check.setTypeItemList(userList);
        CommonListTypeItem<CreateUserEntity> verify = new CommonListTypeItem<CreateUserEntity>("验证人", "最多两人", R.drawable.plus, true);
        verify.setTypeItemList(userList);
        mInfoList.add(verify);
        CommonListTypeItem<CreateUserEntity> m = new CommonListTypeItem<CreateUserEntity>("整改人", "向右滑动查看更多", R.drawable.plus, true);
        m.setTypeItemList(userList);
        mInfoList.add(m);
        CommonListTypeItem<CreateUserEntity> l = new CommonListTypeItem<CreateUserEntity>("查阅人", "向右滑动查看更多", R.drawable.plus, true);
        l.setTypeItemList(userList);
        mInfoList.add(l);
    }

    @Override
    protected void initData() {
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),VERTICAL_LIST);
        dividerItemDecoration.setLineColor(Util.getColor(R.color.line2));
        dividerItemDecoration.setDividerHeight(ViewUtils.dip2px(10));
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(mCommonAdapter);
    }

    @Override
    protected SafeSupervisionContract.Presenter getPresenter() {
        return new SafeSupervisionPresenterImpl();
    }

    @Override
    public void setSafeSupervisionList(List<SafeSupervisionEnity> supervisionList) {
    }

    @Override
    public void fetchPageListData(int page) {
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return null;
    }

    public final String[] imageUrls = new String[]{
            "http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037234_3539.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037234_6318.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037194_2965.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037193_1687.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037193_1286.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037192_8379.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037178_9374.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037177_1254.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037177_6203.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037152_6352.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037151_9565.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037151_7904.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037148_7104.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037129_8825.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037128_5291.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037128_3531.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037127_1085.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037095_7515.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037094_8001.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037093_7168.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037091_4950.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949643_6410.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949642_6939.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949630_4505.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949630_4593.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949629_7309.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949629_8247.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949615_1986.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_8482.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_3743.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949614_4199.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949599_3416.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949599_5269.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949598_7858.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949598_9982.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949578_2770.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949578_8744.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949577_5210.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949577_1998.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949482_8813.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949481_6577.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949480_4490.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949455_6792.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949455_6345.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949442_4553.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949441_8987.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949441_5454.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949454_6367.jpg",
            "http://img.my.csdn.net/uploads/201308/31/1377949442_4562.jpg"};

}
