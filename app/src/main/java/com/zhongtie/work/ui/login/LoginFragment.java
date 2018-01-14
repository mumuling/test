package com.zhongtie.work.ui.login;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BasePresenterFragment;

/**
 * Auth: Chaek
 * Date: 2018/1/9
 */

public class LoginFragment extends BasePresenterFragment<LoginContract.Presenter> implements LoginContract.View {

    private ImageView mLoginSrc;
    private TextView mLoginCompanyName;
    private LinearLayout mLoginNameView;
    private EditText mLoginName;
    private LinearLayout mLoginPasswordView;
    private EditText mLoginPassword;
    private TextView mLogin;

    @Override
    public void loginSuccess() {

    }

    @Override
    public void setLastLoginUserName(String userName) {

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
        mLoginNameView = (LinearLayout) findViewById(R.id.login_name_view);
        mLoginName = (EditText) findViewById(R.id.login_name);
        mLoginPasswordView = (LinearLayout) findViewById(R.id.login_password_view);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLogin = (TextView) findViewById(R.id.login);
    }

    @Override
    protected void initData() {
        mLogin.setOnClickListener(v -> mPresenter.login());
    }
}
