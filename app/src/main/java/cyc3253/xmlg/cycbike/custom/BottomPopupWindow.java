package cyc3253.xmlg.cycbike.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import cyc3253.xmlg.cycbike.R;

/**
 * All rights Reserved, Designed By GeofferySun 
 * @Title: 	BottomPopupWindow.java
 * @Package sun.geoffery.uploadpic
 * @Description:从底部弹出或滑出选择菜单或窗口
 * @author:	GeofferySun
 * @date:	2015年1月15日 上午1:21:01
 * @version	V1.0
 */

public class BottomPopupWindow extends android.widget.PopupWindow {

	private Button firstBtn, secondBtn, cancelBtn;
	private View mMenuView;

	@SuppressLint("InflateParams")
	public BottomPopupWindow(Context context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.layout_popup_window, null);
		firstBtn = (Button) mMenuView.findViewById(R.id.button1);
		secondBtn = (Button) mMenuView.findViewById(R.id.button2);
		cancelBtn = (Button) mMenuView.findViewById(R.id.cancelBtn);
		// 设置按钮监听
		cancelBtn.setOnClickListener(itemsOnClick);
		firstBtn.setOnClickListener(itemsOnClick);
		secondBtn.setOnClickListener(itemsOnClick);

		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x80000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			@Override
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}

	public void setButton1Text(String text){
		firstBtn.setText(text);
	}

	public void setButton2Text(String text){
		secondBtn.setText(text);
	}
}
