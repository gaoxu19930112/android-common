package cn.aorise.common.component.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import cn.aorise.common.R;
import cn.aorise.common.component.common.CmptUtils;
import cn.aorise.common.component.network.entity.response.AccountInfo;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.util.AoriseLog;
import cn.aorise.common.core.util.SPUtils;
import cn.aorise.common.databinding.AoriseActivityComponentLoginBinding;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 公共登录页面
 *     version: 1.0
 * </pre>
 */
public class AoriseLoginActivity extends BaseActivity implements TextWatcher {
    private static final String TAG = AoriseLoginActivity.class.getSimpleName();
    // public static final String PACKAGE_NAME_KEY = "PACKAGE_NAME_KEY";
    // public static final String LOGIN_CLASS_NAME_KEY = "LOGIN_CLASS_NAME_KEY";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private AoriseActivityComponentLoginBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // RxAPIManager.getsInstance().cancel(TAG);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.aorise_activity_component_login);
        // getToolBar().setNavigationIcon(null);

        String account = SPUtils.getInstance().getString(ACCOUNT, "");
        if (!TextUtils.isEmpty(account)) {
            mBinding.etAccount.getEditText().setText(account);
            mBinding.etAccount.getEditText().setSelection(account.length());
        }

        String password = SPUtils.getInstance().getString(PASSWORD, "");
        if (!TextUtils.isEmpty(password)) {
            mBinding.etPassword.getEditText().setText(password);
            mBinding.etPassword.getEditText().setSelection(password.length());
        }

        setLoginEnabled(!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password));
    }

    @Override
    protected void initEvent() {
        mBinding.etAccount.getEditText().addTextChangedListener(this);
        mBinding.etPassword.getEditText().addTextChangedListener(this);

//        mBinding.etPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });

        mBinding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mBinding.etAccount.getEditText().setError(null);
        mBinding.etPassword.getEditText().setError(null);

        // Store values at the time of the login attempt.
        String account = mBinding.etAccount.getEditText().getText().toString();
        String password = mBinding.etPassword.getEditText().getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            // mBinding.etPassword.getEditText().setError(getString(R.string.aorise_component_error_field_required));
            showToast(R.string.aorise_component_error_field_required);
            focusView = mBinding.etPassword.getEditText();
            cancel = true;
        } else if (!isPasswordValid(password)) {
            // mBinding.etPassword.getEditText().setError(getString(R.string.aorise_component_error_invalid_password));
            showToast(R.string.aorise_component_error_invalid_password);
            focusView = mBinding.etPassword.getEditText();
            cancel = true;
        }

        // Check for a valid account address.
        if (TextUtils.isEmpty(account)) {
            // mBinding.etAccount.getEditText().setError(getString(R.string.aorise_component_error_field_required));
            showToast(R.string.aorise_component_error_invalid_password);
            focusView = mBinding.etAccount;
            cancel = true;
        } else if (!isAccountValid(account)) {
            // mBinding.etAccount.getEditText().setError(getString(R.string.aorise_component_error_invalid_account));
            showToast(R.string.aorise_component_error_invalid_account);
            focusView = mBinding.etAccount;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            request(account, password);

            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccount("aorise");
            accountInfo.setId("123456789");
            accountInfo.setSex("male");
            success(accountInfo);
        }
    }

    private void saveLoginInfo(String account, String password) {
        SPUtils.getInstance().put(ACCOUNT, account);
        SPUtils.getInstance().put(PASSWORD, password);
    }

    private void removeLoginInfo() {
        SPUtils.getInstance().remove(ACCOUNT);
        SPUtils.getInstance().remove(PASSWORD);
    }

    private boolean isAccountValid(String account) {
        //TODO: Replace this with your own logic
        return account.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

//    /**
//     * 发送本地广播
//     */
//    @Deprecated
//    private void sendLocalBroadcast() {
//        Intent mIntent = new Intent(AoriseConstant.BroadcastKey.CN_AORISE_PLATFORM_LOGIN_ACCOUNT);
//        mIntent.putExtra(AoriseConstant.AccountKey.USER_ACCOUNT, "奥昇平台");
//        mIntent.putExtra(AoriseConstant.AccountKey.USER_ID, "431125201702133114");
//        mIntent.putExtra(AoriseConstant.AccountKey.USER_SEX, "男");
//        LocalBroadcastManager.getsInstance(this).sendBroadcast(mIntent);
//    }

    private void request(String account, String password) {
//        Subscription subscription = CmptApiService.Factory.create().getLogin()
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.<APIResult<AccountInfo>>bindToLifecycle())
//                .subscribe(CmptUtils.mockSubscriber(this, CmptMock.GET_ACCOUNT
//                        , new TypeToken<APIResult<AccountInfo>>() {
//                        }.getType()
//                        , new APIMockCallback<APIResult<AccountInfo>>() {
//                            @Override
//                            public void onStart() {
//                                AoriseLog.i(TAG, "onStart");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                AoriseLog.e(TAG, "onError:" + e.toString());
//                            }
//
//                            @Override
//                            public void onCompleted() {
//                                AoriseLog.i(TAG, "onCompleted");
//                            }
//
//                            @Override
//                            public void onNext(APIResult<AccountInfo> accountInfoAPIResult) {
//                                AoriseLog.i(TAG, "onNext");
//                                success(accountInfoAPIResult.getData());
//                            }
//
//                            @Override
//                            public void onMock(APIResult<AccountInfo> accountInfoAPIResult) {
//                                AoriseLog.i(TAG, "onMock");
//                                success(accountInfoAPIResult.getData());
//                            }
//                        }
//                ));
//        RxAPIManager.getsInstance().add(TAG, subscription);
    }

    private void success(AccountInfo accountInfo) {
        if (mBinding.cbPassword.isChecked()) {
            saveLoginInfo(mBinding.etAccount.getEditText().getText().toString(),
                    mBinding.etPassword.getEditText().getText().toString());
        } else {
            removeLoginInfo();
        }
        CmptUtils.gotoTargetActivity(this, accountInfo);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean isAccountEmpty = TextUtils.isEmpty(mBinding.etAccount.getEditText().getText().toString());
        boolean isPasswordEmpty = TextUtils.isEmpty(mBinding.etPassword.getEditText().getText().toString());
        AoriseLog.i(TAG, "isAccountEmpty: " + isAccountEmpty + " ;isPasswordEmpty: " + isPasswordEmpty);
        setLoginEnabled(!isAccountEmpty && !isPasswordEmpty);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 登录按钮是否可以生效
     *
     * @param enabled true生效 false不生效
     */
    private void setLoginEnabled(boolean enabled) {
        AoriseLog.i(TAG, "enabled: " + enabled);
        mBinding.btnSignIn.setEnabled(enabled);
    }
}
