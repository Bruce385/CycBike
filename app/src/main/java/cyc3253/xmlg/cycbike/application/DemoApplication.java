package cyc3253.xmlg.cycbike.application;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.io.File;
import java.util.Stack;

import cn.bmob.v3.Bmob;

public class DemoApplication extends Application {

    public static String mSDCardPath;
    public static final String APP_FOLDER_NAME = "CycBike";
    private static Stack<Activity> activityStack;
    private static DemoApplication domoApplication;

    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5a068b52");
        Bmob.initialize(this,"b69e0d4fd614b253f960d8352e596744");
        initDirs();
        domoApplication = this;
    }

    private boolean initDirs() {
        mSDCardPath = Environment.getExternalStorageDirectory().toString();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
