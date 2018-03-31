package cyc3253.xmlg.cycbike.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import cyc3253.xmlg.cycbike.R;

/**
 * Created by CYC on 2018/1/5.
 */

public class CommomDialog extends CustomDialog{

    private String title;
    private String content;

    public CommomDialog(Context context, String content, OnCloseListener closeListener) {
        super(context, closeListener);
        this.content = content;

    }

    public CommomDialog(Context context, String title, String content, OnCloseListener closeListener) {
        super(context, closeListener);
        this.title = title;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        TextView contentTxt = (TextView) findViewById(R.id.content);
        contentTxt.setText(content);
        TextView titleTxt = (TextView) findViewById(R.id.title);
        if (!TextUtils.isEmpty(title)) titleTxt.setText(title);
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
    }


}