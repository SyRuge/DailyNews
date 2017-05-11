package com.xcx.dailynews.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.LoginBean;
import com.xcx.dailynews.di.component.AppComponent;
import com.xcx.dailynews.di.component.DaggerFragmentComponent;
import com.xcx.dailynews.di.module.ActivityModule;
import com.xcx.dailynews.mvp.presenter.LoginPresenter;
import com.xcx.dailynews.mvp.presenter.NewsContract;
import com.xcx.dailynews.util.UiUtil;
import com.xcx.dailynews.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends AppCompatActivity implements NewsContract.View<List<LoginBean>> {

    @Bind(R.id.input_email)
    EditText mInputEmail;
    @Bind(R.id.input_password)
    EditText mInputPassword;
    @Bind(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @Bind(R.id.qq_login)
    ImageView mQqLogin;
    @Bind(R.id.sms_login)
    ImageView mSmsLogin;
    @Bind(R.id.ti_login_email)
    TextInputLayout mTiLoginEmail;
    @Bind(R.id.ti_login_pwd)
    TextInputLayout mTiLoginPwd;
    @Bind(R.id.tv_link_signup)
    TextView mTvLinkSignup;
    private Tencent mTencent;
    private BaseUiListener mLoginListener;

    @Inject
    LoginPresenter mLoginPresenter;

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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initThirdSDK();
        initInject();
        init();
    }

    private void initThirdSDK() {
        ShareSDK.initSDK(this, "1cca67705dc55");
        SMSSDK.initSDK(this, "1cca397f74a6c", "7299c6f48436732111bcca0a33f5a2e1");
    }

    private void initInject() {
        AppComponent appComponent = ((MyApplication) getApplication())
                .getAppComponent();
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .injectLoginActivity(this);
    }

    private void init() {
        ViewUtil.changeStatusBarColor(this);
        mTencent = Tencent.createInstance("1106089018", UiUtil.getAppContext());
        mLoginListener = new BaseUiListener();
        //   mMyEventHandler = new MyEventHandler();
        //   SMSSDK.registerEventHandler(mMyEventHandler);
    }

    @OnClick({R.id.input_email, R.id.input_password, R.id.btn_login, R.id.qq_login, R.id
            .sms_login, R.id.tv_link_signup})
    public void onViewClicked(View view ) {
        switch (view.getId()) {
            case R.id.input_email:
                break;
            case R.id.input_password:
                break;
            case R.id.btn_login:
                String name = mInputEmail.getText().toString();
                String password = mInputPassword.getText().toString();
                //   validate(name, password);
                login(name, password);
                break;
            case R.id.qq_login:
                qqThirdLogin();
                break;
            case R.id.sms_login:
                smsThirdLogin();
                break;
            case R.id.tv_link_signup:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
              //  startActivity(intent);

                startActivityForResult(intent,100);

              //  finish();
                break;
        }
    }


    private void login(String loginName, String password) {

        hideKeyboard();
        //  mInputEmail.setError(null);
        mTiLoginEmail.setError(null);
        mTiLoginPwd.setError(null);

        mLoginPresenter.attachView(this);
        matcher = pattern.matcher(loginName);
        if (!matcher.matches()) {
            mTiLoginEmail.setError("不符合邮箱格式!");
            return;
        } else if (TextUtils.isEmpty(password) || password.length() < 3) {
            mTiLoginPwd.setError("密码长度小于三位!");
            return;
        } else {
            mLoginPresenter.getData(loginName, password, -1, -1);
            mTiLoginEmail.setErrorEnabled(false);
            mTiLoginEmail.setErrorEnabled(false);
            initPb();
        }
    }

    private void initPb() {
        mProgressDialog = new ProgressDialog(LoginActivity.this,
               R.style.Theme_AppCompat_DayNight_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("正在登陆...");
        mProgressDialog.show();
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

    /**
     * QQ第三方登陆
     */
    private void qqThirdLogin() {

     //   mTencent.setOpenId("");
     //   mTencent.setAccessToken("","");

        /**
         * new IUiListener() {
        @Override
        public void onComplete(Object o) {
        Log.e("返回", o.toString());
        // JSONObject jo= (JSONObject) o;
        JSONObject jo= (JSONObject) o;
        int ret=jo.getInteger("ret");
        if(ret==0) {
        Toast.makeText(LoginActivity.this, "登录成功",
        Toast.LENGTH_LONG).show();
        String openID = jo.getString("openid");
        String accessToken = jo.getString("access_token");
        String expires = jo.getString("expires_in");
        //下面两个方法非常重要，否则会出现client request's parameters are invalid, invalid openid
        mTencent.setOpenId(openID);
        mTencent.setAccessToken(accessToken, expires);
        }
        }

        @Override
        public void onError(UiError uiError) {
        Toast.makeText(getApplicationContext(),"登录错误",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
        Toast.makeText(getApplicationContext(),"登录取消",Toast.LENGTH_LONG).show();
        }
        }
         */

        //all获取全部
        mTencent.login(this, "all", mLoginListener);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Log.i("onActivityResult", data.getStringExtra("key_action"));
                //如果少了这句，监听器的没效果。onComplete不会执行
                Tencent.handleResultData(data, mLoginListener);
            }
        }

        if (requestCode==100){
            if (resultCode==1){
                setResult(1);
                finish();
            }else if (resultCode==-1){

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 短信验证注册登录
     */
    private void smsThirdLogin() {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息（此方法可以不调用）
                    //  Toast.makeText(getApplicationContext(), "登陆成功啦", Toast.LENGTH_SHORT).show();
                    setResult(1);
                    finish();

                }
            }
        });
        registerPage.show(this);
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
    public void setDataToView(List<LoginBean> list, int loadType) {
        if (list != null && list.size() != 0) {
            LoginBean bean = list.get(0);
            if (bean.status.equals("success")) {
                Toast.makeText(getApplicationContext(), "登陆成功!", Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();
            } else if (bean.status.equals("failure")) {
                Toast.makeText(getApplicationContext(), "用户名不存在!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "服务器异常!", Toast.LENGTH_SHORT).show();
        }
        mProgressDialog.dismiss();
    }

    private void setLoginStatus() {

    }


    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            JSONObject jo= (JSONObject) o;
            int ret= 0;
            try {
                ret = jo.getInt("ret");
                if(ret==0) {
                    Toast.makeText(LoginActivity.this, "登录成功!",
                            Toast.LENGTH_LONG).show();
                    String openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    //下面两个方法非常重要，否则会出现client request's parameters are invalid, invalid openid
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                }
                setResult(1);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError error) {

        }

        @Override
        public void onCancel() {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        //  SMSSDK.unregisterEventHandler(mMyEventHandler);
    }
    @Override
    public void onBackPressed() {
        setResult(-2);
        finish();
        super.onBackPressed();
    }
}
