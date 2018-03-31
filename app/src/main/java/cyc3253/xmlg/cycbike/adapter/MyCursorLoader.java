package cyc3253.xmlg.cycbike.adapter;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * .
 */

public class MyCursorLoader extends CursorLoader {

    String[] mContactProjection={
            ContactsContract.Contacts._ID, //0
            ContactsContract.Contacts.DISPLAY_NAME//1
    };

    private Context mContext;
    public MyCursorLoader(Context context) {
        super(context);
        mContext = context;
    }
    /**
     * 查询数据等操作放在这里执行
     */
    @Override
    protected Cursor onLoadInBackground() {
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                mContactProjection, null,null, null);
        return cursor;
    }
}
