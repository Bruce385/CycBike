package cyc3253.xmlg.cycbike.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import cyc3253.xmlg.cycbike.R;

/**
 * Created by CYC on 2018/1/6.
 */

public abstract class CustomDialog extends Dialog implements View.OnClickListener {

    private OnCloseListener closeListener;

    public CustomDialog(Context context, OnCloseListener closeListener) {
        super(context, R.style.dialog);
        this.closeListener = closeListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (closeListener != null) {
                    closeListener.onClose(false);
                }
                break;
            case R.id.submit:
                if (closeListener != null) {
                    closeListener.onClose(true);
                }
                break;
        }
        this.dismiss();
    }

    public interface OnCloseListener {
        void onClose(boolean confirm);
    }
}
