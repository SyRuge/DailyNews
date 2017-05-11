package com.xcx.dailynews.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.LoginBean;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerFragmentComponent;
import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.mvp.presenter.NewsContract;
import com.xcx.dailynews.mvp.presenter.SignUpPresenter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements NewsContract
        .View<List<LoginBean>> {

    @Bind(R.id.et_sign_email)
    EditText mEtSignEmail;
    @Bind(R.id.ti_sign_email)
    TextInputLayout mTiSignEmail;
    @Bind(R.id.et_sign_password)
    EditText mEtSignPassword;
    @Bind(R.id.ti_sign_pwd)
    TextInputLayout mTiSignPwd;
    @Bind(R.id.et_sign_conpwd)
    EditText mEtSignConpwd;
    @Bind(R.id.ti_sign_conpwd)
    TextInputLayout mTiSignConpwd;
    @Bind(R.id.btn_sign_up)
    AppCompatButton mBtnSignUp;
    @Bind(R.id.tv_link_login)
    TextView mTvLinkLogin;
    @Inject
    SignUpPresenter mSignUpPresenter;

    /**
     * 邮箱验证
     */
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;" +
            "<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initInject();
    }

    private void initInject() {
        AppComponent appComponent = ((MyApplication) getApplication())
                .getAppComponent();
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .injectSignUpActivity(this);
    }

    @OnClick({R.id.btn_sign_up, R.id.tv_link_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                String email = mEtSignEmail.getText().toString();
                String pwd = mEtSignPassword.getText().toString();
                String conpwd = mEtSignConpwd.getText().toString();
                signUp(email, pwd, conpwd);
                break;
            case R.id.tv_link_login:
                setResult(-1);
                finish();
                break;
        }
    }

    private void signUp(String email, String pwd, String conpwd) {
        hideKeyboard();

        mSignUpPresenter.attachView(this);
        matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            mTiSignEmail.setError("不符合邮箱格式!");
            return;
        } else if (TextUtils.isEmpty(pwd) || pwd.length() < 3) {
            mTiSignPwd.setError("密码长度小于三位!");
            return;
        } else if (!pwd.equals(conpwd)) {
            mTiSignConpwd.setError("两次输入密码不一致!");
        } else {
            mSignUpPresenter.getData(email, pwd, -1, -1);
            mTiSignEmail.setErrorEnabled(false);
            mTiSignPwd.setErrorEnabled(false);
            mTiSignConpwd.setErrorEnabled(false);
            initPb();

        }
    }

    private void initPb() {
        mProgressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("正在注册...");
        mProgressDialog.show();
    }

    @Override
    public void setDataToView(List<LoginBean> list, int loadType) {
        if (list != null && list.size() != 0) {
            LoginBean bean = list.get(0);
            if (bean.status.equals("success")) {
                Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();
            } else if (bean.status.equals("exists")) {
                Toast.makeText(getApplicationContext(), "账号已经存在!", Toast.LENGTH_SHORT).show();
            }else{
             //   Toast.makeText(getApplicationContext(), "账号已经存在!", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "服务器异常!", Toast.LENGTH_SHORT).show();
        }
        mProgressDialog.dismiss();
    }

    /**
     * 当点击登录时，隐藏按钮
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager
                            .HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void showNetErrorMessage() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showErrorMessage(String message) {
        mProgressDialog.dismiss();
    }
    @Override
    public void onBackPressed() {
        setResult(-2);
        finish();
        super.onBackPressed();
    }
}
