package cyc3253.xmlg.cycbike.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cyc3253.xmlg.cycbike.R;

/**
 * 侧滑菜单.
 */

public class LeftMenuFragment extends Fragment {

    private ImageView userPhoto;
    private TextView userName, userNum;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_menu,null);
        userPhoto = (ImageView) view.findViewById(R.id.user_photo);
        userName = (TextView) view.findViewById(R.id.user_name);
        userNum = (TextView) view.findViewById(R.id.user_num);
        return view;
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public void setUserNum(String userNum) {
        this.userNum.setText(userNum.substring(0, 3) + "*****" + userNum.substring(8, userNum.length()));
    }

    public ImageView getUserPhoto() {
        return userPhoto;
    }
}
