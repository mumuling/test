package com.zhongtie.work.ui.login;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongtie.work.R;
import com.zhongtie.work.ui.base.BasePresenterActivity;
import com.zhongtie.work.ui.main.MainActivity;

/**
 * Date: 2018/1/9
 *
 * @author Chaek
 */

public class LoginActivity extends BasePresenterActivity<LoginContract.Presenter> implements LoginContract.View {
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
    public void loginFail() {
        startActivity(new Intent(getAppContext(), MainActivity.class));
        finish();
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
//        mLogin.setOnClickListener(v -> mPresenter.login());
        mLogin.setOnClickListener(v ->loginFail());
        mLoginCompanyName.post(this::requestLoginLayout);
    }

    private void requestLoginLayout() {
        findViewById(R.id.login_edit_view).getLayoutParams().width = mLoginCompanyName.getMeasuredWidth();
        findViewById(R.id.login_edit_view).requestLayout();
    }
}
