package jp.co.devjchankchan.slackapilibrary;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class URLConnectionTaskLoader extends AsyncTaskLoader {

    public URLConnectionTaskLoader(Context context) {
        super(context);
    }

    @Override
    public Object loadInBackground() {
        return null;
    }
}
