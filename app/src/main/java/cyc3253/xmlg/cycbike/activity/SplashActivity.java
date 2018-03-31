package cyc3253.xmlg.cycbike.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import cn.bmob.v3.BmobUser;
import cyc3253.xmlg.cycbike.MainActivity;
import cyc3253.xmlg.cycbike.R;
import cyc3253.xmlg.cycbike.bean.User;

/**启动界面
 * @author :CYC
 * @project:SplashActivity
 * @date :2017-11-15-18:23
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler =new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = BmobUser.getCurrentUser(User.class);
                if (user == null) {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        },1000);

    }
}
