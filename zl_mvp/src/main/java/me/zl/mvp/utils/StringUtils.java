package me.zl.mvp.utils;

import android.text.Html;
import android.widget.TextView;

public class StringUtils {

    public static void setHtml(TextView tv, String content){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(content,Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv.setText(Html.fromHtml(content));
        }
    }

    public static String getStringNum(double f){
        if((f * 100) % 100 == 0){
            return ((int) f) + "";
        }else{
            return f+"";
        }
    }
}
