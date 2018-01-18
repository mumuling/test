package com.zhongtie.work.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BasePresenterActivity;
import com.zhongtie.work.ui.main.MainActivity;
import com.zhongtie.work.util.TextUtil;

/**
 * Date: 2018/1/9
 *
 * @author Chaek
 */

public class LoginActivity extends BasePresenterActivity<LoginContract.Presenter> implements LoginContract.View {
    private ImageView mLoginSrc;
    private TextView mLoginCompanyName;
    private EditText mLoginName;
    private EditText mLoginPassword;
    private TextView mLogin;


    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
    @Override
    public void loginSuccess() {
        startActivity(new Intent(getAppContext(), MainActivity.class));
        finish();
    }

    @Override
    public void setLastLoginUserName(String userName) {
        mLoginName.setText(userName);
        if (!TextUtil.isEmpty(userName)) {
            mLoginName.setSelection(userName.length());
        }
    }


    @Override
    public void loginFail() {

    }

    @Override
    public String getUserPhone() {
        return mLoginName.getText().toString();
    }

    @Override
    public String getUserPassword() {
        return mLoginPassword.getText().toString();
    }

    @Override
    protected LoginContract.Presenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        mLoginSrc = (ImageView) findViewById(R.id.login_src);
        mLoginCompanyName = (TextView) findViewById(R.id.login_company_name);
        mLoginName = (EditText) findViewById(R.id.login_name);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLogin = (TextView) findViewById(R.id.login);

    }

    @Override
    protected void initData() {
        mPresenter.fetchCacheUserName();

        mLogin.setOnClickListener(view -> {
            mPresenter.login();
            hideInput();
        });
        mLoginCompanyName.post(this::requestLoginLayout);
    }

    @SuppressLint("WrongViewCast")
    private void requestLoginLayout() {
        findViewById(R.id.login_edit_view).getLayoutParams().width = mLoginCompanyName.getMeasuredWidth();
        findViewById(R.id.login_edit_view).requestLayout();
    }
}
