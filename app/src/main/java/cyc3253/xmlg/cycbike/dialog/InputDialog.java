package cyc3253.xmlg.cycbike.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cyc3253.xmlg.cycbike.R;

/**
 * Created by CYC on 2018/1/5.
 */

public class InputDialog extends Dialog implements View.OnClickListener {

    private String title;
    private int icon;
    private OnCloseListener closeListener;
    private EditText input;

    public InputDialog(@NonNull Context context, String title, OnCloseListener closeListener) {
        super(context, R.style.dialog);
        this.title = title;
        this.closeListener = closeListener;
    }

    public InputDialog(@NonNull Context context, String title, int icon, OnCloseListener closeListener) {
        super(context, R.style.dialog);
        this.title = title;
        this.icon = icon;
        this.closeListener = closeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        ImageView ivIcon = (ImageView) findViewById(R.id.icon);
        ivIcon.setImageResource(icon);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(title);
        input = (EditText) findViewById(R.id.input);
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                break;
            case R.id.submit:
                closeListener.onClose(String.valueOf(input.getText()).trim());
                break;
        }
        this.dismiss();
    }

    public interface OnCloseListener {
        void onClose(String input);
    }

}
