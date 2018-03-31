package cyc3253.xmlg.cycbike.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cyc3253.xmlg.cycbike.MainActivity;
import cyc3253.xmlg.cycbike.R;
import cyc3253.xmlg.cycbike.bean.User;
import cyc3253.xmlg.cycbike.custom.BottomPopupWindow;
import cyc3253.xmlg.cycbike.dialog.CommomDialog;
import cyc3253.xmlg.cycbike.utils.CheckInputUtils;
import cyc3253.xmlg.cycbike.utils.KeyBoardUtil;
import rx.functions.Action1;

public class LoginActivity extends Activity {

    private ImageView userHeadImg;
    private TextView nickName;
    private View filler;
    private EditText phoneNumber, password;
    private Button btnLogin, btnRegister, btnForgotPassword;
    private ImageButton cancalPhoneNum, cancalPassword, passwordEye;
    private boolean isOpen = false;
    private BottomPopupWindow menuWindow; // 自定义的弹出框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        addTextChanged();
        loginClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyBoardUtil.setOnKeyBoardChangeListener(this, new KeyBoardUtil.OnKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                userHeadImg.setVisibility(View.GONE);
                filler.setVisibility(View.VISIBLE);
                nickName.setTextSize(26);
            }

            @Override
            public void keyBoardHide(int height) {
                userHeadImg.setVisibility(View.VISIBLE);
                filler.setVisibility(View.GONE);
                nickName.setTextSize(20);
            }
        });
    }

    private void initView() {
        userHeadImg = (ImageView) findViewById(R.id.userHeadImg);
        nickName = (TextView) findViewById(R.id.userNameTxt);
        filler = findViewById(R.id.filler);
        phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.loginBtn);

        btnRegister = (Button) findViewById(R.id.registerBtn);
        btnRegister.setOnClickListener(new EditButtonOnClickListener());
        btnForgotPassword = (Button) findViewById(R.id.forgot_passwordBtn);
        btnForgotPassword.setOnClickListener(new EditButtonOnClickListener());

        cancalPhoneNum = (ImageButton) findViewById(R.id.bt_username_clear);
        cancalPhoneNum.setOnClickListener(new EditButtonOnClickListener());
        cancalPassword = (ImageButton) findViewById(R.id.bt_password_clear);
        cancalPassword.setOnClickListener(new EditButtonOnClickListener());
        passwordEye = (ImageButton) findViewById(R.id.bt_pwd_eye);
        passwordEye.setOnClickListener(new EditButtonOnClickListener());
    }

    private void addTextChanged() {
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = phoneNumber.getText().toString().trim();
                if ("".equals(user)) {
                    cancalPhoneNum.setVisibility(View.INVISIBLE);
                } else {
                    cancalPhoneNum.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = password.getText().toString().trim();
                if ("".equals(pwd)) {
                    cancalPassword.setVisibility(View.INVISIBLE);
                    passwordEye.setVisibility(View.INVISIBLE);
                } else {
                    cancalPassword.setVisibility(View.VISIBLE);
                    passwordEye.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loginClick() {
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkLogin = checkLogin();
                if (checkLogin != null) {
                    new CommomDialog(LoginActivity.this, "输入有误", checkLogin,
                            new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClose(boolean confirm) {
                                }
                            }).show();
                } else {
                    BmobUser.loginByAccountObservable(User.class, phoneNumber.getText().toString(),
                            password.getText().toString()).subscribe(new Action1<User>() {
                        @Override
                        public void call(User user) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Toast.makeText(LoginActivity.this,
                                    throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private String checkLogin() {
        if (CheckInputUtils.checkPhoneNum(phoneNumber.getText().toString()) != null)
            return CheckInputUtils.checkPhoneNum(phoneNumber.getText().toString());

        if (CheckInputUtils.checkPassword(password.getText().toString()) != null)
            return CheckInputUtils.checkPassword(password.getText().toString());

        return null;
    }

    class EditButtonOnClickListener implements OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_username_clear:
                    // 清除登录名
                    phoneNumber.setText("");
                    break;
                case R.id.bt_password_clear:
                    // 清除密码
                    password.setText("");
                    break;
                case R.id.bt_pwd_eye:
                    // 密码可见与不可见的切换
                    if (isOpen) {
                        isOpen = false;
                    } else {
                        isOpen = true;
                    }
                    // 默认isOpen是false,密码不可见
                    changePwdOpenOrClose(isOpen);
                    break;
                case R.id.registerBtn:
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.forgot_passwordBtn:
                    menuWindow = new BottomPopupWindow(LoginActivity.this, itemsOnClick);
                    menuWindow.setButton1Text("找回密码");
                    menuWindow.setButton2Text("短信验证登录");
                    menuWindow.showAtLocation(findViewById(R.id.mainLayout),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
                default:
                    break;
            }
        }

        private void changePwdOpenOrClose(boolean flag) {
            // 第一次过来是false，密码不可见
            if (flag) {
                // 密码可见
                passwordEye.setImageResource(R.mipmap.eye_open);
                // 设置EditText的密码可见
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // 密码不可见
                passwordEye.setImageResource(R.mipmap.eye_close);
                // 设置EditText的密码隐藏
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    private OnClickListener itemsOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 找回密码
                case R.id.button1:

                    break;
                // 短信登录
                case R.id.button2:

                    break;
                default:
                    break;
            }
        }
    };

}
