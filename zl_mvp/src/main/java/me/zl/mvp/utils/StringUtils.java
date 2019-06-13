package me.zl.mvp.utils;

import android.text.Html;
import android.widget.TextView;

public class StringUtils {

    //StringUtils.setHtml(tvContent,String.format(string,entity.getLawyerPriceStr(),entity.getBalanceUnit(),entity.getPriceUnit()));
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
            int i = (int) (f * 100);
            int a = i/100;
            int b = i%100;
            String result = a+"."+b;
            if(result.endsWith("0")){
                return result.substring(0,result.length()-1);
            }
            return result;

        }
    }

    /***
     * 获取url 指定name的value;
     * @param url
     * @param name
     * @return
     */
    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }
}
