package cn.lex_mung.client_android.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

public class BadgeNumUtil {

    Context context;

    public BadgeNumUtil(Context context) {
        this.context = context;
    }

    boolean mIsSupportedBade = true;

    public void setHuaweiBadgeNum(int num) {
        if (mIsSupportedBade) {
            setBadgeNum(num);
        }
    }

    /**
     * set badge number
     */
    public void setBadgeNum(int num) {
        try {
            Bundle bunlde = new Bundle();
            bunlde.putString("package", "cn.lex_mung.client_android"); // com.test.badge is your package name
            bunlde.putString("class", "cn.lex_mung.client_android.mvp.ui.activity.LaunchActivity"); // com.test. badge.MainActivity is your apk main activity
            bunlde.putInt("badgenumber", num);
            context.getApplicationContext().getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e) {
            mIsSupportedBade = false;
            LogUtil.e("serBadgeNum:" + e);
        }
    }


}
