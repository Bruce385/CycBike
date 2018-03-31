package cyc3253.xmlg.cycbike.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cyc3253.xmlg.cycbike.R;
import cyc3253.xmlg.cycbike.bean.User;
import cyc3253.xmlg.cycbike.dialog.CommomDialog;
import cyc3253.xmlg.cycbike.utils.CheckInputUtils;
import cyc3253.xmlg.cycbike.utils.KeyBoardUtil;
import rx.functions.Action1;

public class RegisterActivity extends Activity {

    private TextView tvRemark;
    private View filler;
    private EditText etPhoneNumber, etPassword, etPassword2;
    private ImageButton phoneNumberClear, passwordClear, passwordClear2;
    private Button btnRegister;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        addTextChanged();
        registerClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyBoardUtil.setOnKeyBoardChangeListener(this, new KeyBoardUtil.OnKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                filler.setVisibility(View.GONE);
            }
            @Override
            public void keyBoardHide(int height) {
                filler.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView() {
        tvRemark = (TextView) findViewById(R.id.tv_remark);
        filler=findViewById(R.id.filler);
        etPhoneNumber = (EditText) findViewById(R.id.et_phoneNum);
        etPassword = (EditText) findViewById(R.id.et_password1);
        etPassword2 = (EditText) findViewById(R.id.et_password2);

        phoneNumberClear = (ImageButton) findViewById(R.id.bt_phoneNum_clear);
        phoneNumberClear.setOnClickListener(new MyButtonOnClickListener());
        passwordClear = (ImageButton) findViewById(R.id.bt_password_clear1);
        passwordClear.setOnClickListener(new MyButtonOnClickListener());
        passwordClear2 = (ImageButton) findViewById(R.id.bt_password_clear2);
        passwordClear2.setOnClickListener(new MyButtonOnClickListener());

        btnRegister = (Button) findViewById(R.id.registerFirstBtn);
    }

    private void addTextChanged() {
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = etPhoneNumber.getText().toString().trim();
                if ("".equals(user)) {
                    phoneNumberClear.setVisibility(View.INVISIBLE);
                } else {
                    phoneNumberClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = etPassword.getText().toString().trim();
                if ("".equals(user)) {
                    passwordClear.setVisibility(View.INVISIBLE);
                } else {
                    passwordClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = etPassword2.getText().toString().trim();
                if ("".equals(user)) {
                    passwordClear2.setVisibility(View.INVISIBLE);
                } else {
                    passwordClear2.setVisibility(View.VISIBLE);
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

    private void registerClick() {
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkRegister = checkRegister();
                if (checkRegister != null) {
                    new CommomDialog(RegisterActivity.this, "输入有误",checkRegister, new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClose(boolean confirm) {
                        }
                    }).show();
                } else {
                    User user = new User();
                    user.setUsername(etPhoneNumber.getText().toString());
                    user.setPassword(etPassword.getText().toString());
                    user.signUpObservable(User.class).subscribe(new Action1<User>() {
                        @Override
                        public void call(User user) {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Toast.makeText(RegisterActivity.this,
                                    throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private String checkRegister() {
        if (CheckInputUtils.checkPhoneNum(etPhoneNumber.getText().toString()) != null)
            return CheckInputUtils.checkPhoneNum(etPhoneNumber.getText().toString());

        if (CheckInputUtils.checkPassword(etPassword.getText().toString()) != null)
            return CheckInputUtils.checkPassword(etPassword.getText().toString());

        //验证密码不能为空，并且与一次密码一致
        if (etPassword2.getText().toString() == null ||
                etPassword2.getText().toString().trim().equals("")) {
            return "确认密码不能为空";
        } else {
            if (!etPassword2.getText().toString().equals(etPassword.getText().toString())) {
                return "两次密码输入要一致";
            }
        }
        return null;
    }


    class MyButtonOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_phoneNum_clear:
                    etPhoneNumber.setText("");
                    break;
                case R.id.bt_password_clear1:
                    etPassword.setText("");
                    break;
                case R.id.bt_password_clear2:
                    etPassword2.setText("");
                    break;

                default:
                    break;
            }
        }
    }

}
