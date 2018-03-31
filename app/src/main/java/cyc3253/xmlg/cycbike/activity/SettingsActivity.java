package cyc3253.xmlg.cycbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;
import cyc3253.xmlg.cycbike.R;
import cyc3253.xmlg.cycbike.dialog.CommomDialog;

/**
 * Created by CYC on 2017/11/25.
 */

public class SettingsActivity extends BaseActivity {

    private Button exitButton;
    private ImageView imInform, imAutoPay, imAutoLogin;
    private TextView tvInform, tvAutoPay, tvAutoLogin;
    private ViewGroup cleanCache;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setStatusBar();
        initView();
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommomDialog(SettingsActivity.this, "退出当前用户", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClose(boolean confirm) {
                        if (confirm) userExit();//处理确认按钮的点击事件
                    }
                }).show();
            }
        });
    }

    private void initView() {
        exitButton = (Button) findViewById(R.id.exit);
        imInform = (ImageView) findViewById(R.id.bt_inform);
        imAutoPay = (ImageView) findViewById(R.id.bt_autoPay);
        imAutoLogin = (ImageView) findViewById(R.id.bt_autoLogin);
        tvInform = (TextView) findViewById(R.id.tv_inform);
        tvAutoPay = (TextView) findViewById(R.id.tv_autoPay);
        tvAutoLogin = (TextView) findViewById(R.id.tv_autoLogin);
        cleanCache = (ViewGroup) findViewById(R.id.clean_cache);
    }

    private void userExit() {
        BmobUser.logOut();
        Intent intent = new Intent();
        intent.setAction("Ending.MainActivity"); // 说明动作
        sendBroadcast(intent);// 该函数用于发送广播
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
