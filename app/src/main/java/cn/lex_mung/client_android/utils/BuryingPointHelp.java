package cn.lex_mung.client_android.utils;

import android.app.Activity;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.lex_mung.client_android.BuildConfig;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.model.api.Api;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.DeviceUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static cn.lex_mung.client_android.app.DataHelperTags.IS_LOGIN_SUCCESS;

public class BuryingPointHelp {
    public static final String EVENT_URL = Api.APP_DOMAIN + "static/e.gif";
    public static final String PAGE_URL = Api.APP_DOMAIN + "static/p.gif";

    private volatile static BuryingPointHelp INSTANCE;

    //获取单例
    public static BuryingPointHelp getInstance() {
        if (INSTANCE == null) {
            synchronized (BuryingPointHelp.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BuryingPointHelp();
                }
            }
        }
        return INSTANCE;
    }

    //事件（服务器，友盟）
    public void onEvent(Activity mActivity, String pageName, String eventName) {
        LogUtil.e("onEvent:pageName:" + pageName);
        LogUtil.e("onEvent:eventName:" + eventName);
        MobclickAgent.onEvent(mActivity, eventName);
        sendHttp(EVENT_URL, getEnvenData(mActivity, pageName, eventName));
    }

    //fragment 页面 （服务器，友盟）
    public void onFragmentResumed(Activity mActivity, String pageName) {
        LogUtil.e("onFragmentResumed:pageName:" + pageName);
        LogUtil.e("onFragmentResumed:pageEvent:" + "start");
        MobclickAgent.onPageStart(pageName);
        sendHttp(PAGE_URL, getPageData(mActivity, pageName, "start"));
    }

    //fragment 页面 （服务器，友盟）
    public void onFragmentPaused(Activity mActivity, String pageName) {
        LogUtil.e("onFragmentPaused:pageName:" + pageName);
        LogUtil.e("onFragmentPaused:pageEvent:" + "end");
        MobclickAgent.onPageEnd(pageName);
        sendHttp(PAGE_URL, getPageData(mActivity, pageName, "end"));
    }

    //activity 页面（服务器）
    public void onActivityResumed(Activity mActivity, String pageName) {
        LogUtil.e("onActivityResumed:pageName:" + pageName);
        LogUtil.e("onActivityResumed:pageEvent:" + "start");
        sendHttp(PAGE_URL, getPageData(mActivity, pageName, "start"));
    }

    //activity 页面（服务器）
    public void onActivityPaused(Activity mActivity, String pageName) {
        LogUtil.e("onActivityPaused:pageName:" + pageName);
        LogUtil.e("onActivityPaused:pageEvent:" + "end");
        sendHttp(PAGE_URL, getPageData(mActivity, pageName, "end"));
    }


    //https://api-test.lex-mung.com/static/p.gif
    public void sendHttp(String url, Map<String, Object> map) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("BuryingPointHelp:onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e("BuryingPointHelp:onResponse:" + response.body().toString());
            }
        });
    }

    public Map<String, Object> getPageData(Activity mActivity, String pageName, String pageEvent) {
        Map<String, Object> map = new HashMap<>();
        /*
        1   session	会话id，有效期24小时，可前端自行生成保持24小时,最长128位	是	[string]	fdfdf-reregre-bjfdrem-fdrekre	查看
        2	deviceId	设备id，安卓取imei，其他可以根据uuid生成，并保存到缓存中	是	[string]
        3	deviceType	终端类型：1.安卓-律师端,2.安卓-用户端；3.iOS-律师端;4,iOS-用户端，5.小程序，6终端机,7.PCweb端，8.落地页，9.移动web端，10活动页H5	是	[int]
        4	deviceModel	app传设备厂家和型号，web传浏览器型号，小程序传wechat	是	[string]
        5	pageName	页面名，小写字母，命名以有实际意义的名字命名，如flogin_page	是	[string]
        6	from	来源，上一个页面名称，首页不需要传		[string]
        7	pageEvent	页面动作，取值start/end表示入或退出	是	[string]
        8	netType	wifi/4g/3g/3g/other	是	[string]
        9	channel	渠道值	是	[string]
        10	memberId	会员id，只有登录了才传		[int]
        11	url	H5/web的页面路径，不含域名部分		[string]
        12	appVersion	版本号,如1.0.0	是	[string]
        13	osVersion	系统版本，app传，其他可不传		[string]
        14	ip	H5/web可传，其他端可不传		[string]
        15	isDebug	1为测试数据，0为正式，测试版传1，正式版传0	是	[int]
        16	longitude	经度，如116.2345		[double]
        17	latitude	纬度,如26.3240		[string]
         */
        map.put("session", getSession(mActivity));
        map.put("deviceId", DeviceUtils.getAndroidId(mActivity, getUUid(mActivity)));
        map.put("deviceType", 2);
        map.put("deviceModel", android.os.Build.BRAND + " " + android.os.Build.MODEL);
        map.put("pageName", pageName);
//        map.put("from", "");
        map.put("pageEvent", pageEvent);
        map.put("netType", DeviceUtils.getNetworkType(mActivity) == 1 ? "wifi" : "4g");
        map.put("channel", DataHelper.getStringSF(mActivity, DataHelperTags.CHANNEL));
        if (getMemberId(mActivity) > 0) {
            map.put("memberId", getMemberId(mActivity));
        }
//        map.put("url", 1);
        map.put("appVersion", DeviceUtils.getVersionName(mActivity));
        map.put("osVersion", android.os.Build.VERSION.RELEASE);
//        map.put("ip", 1);
        map.put("isDebug", BuildConfig.IS_PROD ? 0 : 1);
//        map.put("longitude", 1);
//        map.put("latitude", 1);
        return map;
    }

    public Map<String, Object> getEnvenData(Activity mActivity, String pageName, String eventName) {
        Map<String, Object> map = new HashMap<>();
        /*
        1   session	会话id，有效期24小时，可前端自行生成保持24小时,最长128位	是	[string]	fdfdf-reregre-bjfdrem-fdrekre	查看
        2	deviceId	设备id，安卓取imei，其他可以根据uuid生成，并保存到缓存中	是	[string]
        3	deviceType	终端类型：1.安卓-律师端,2.安卓-用户端；3.iOS-律师端;4,iOS-用户端，5.小程序，6终端机,7.PCweb端，8.落地页，9.移动web端，10活动页H5	是	[int]
        4	deviceModel	app传设备厂家和型号，web传浏览器型号，小程序传wechat	是	[string]
        5	pageName	页面名，小写字母，命名以有实际意义的名字命名，如flogin_page	是	[string]
        6	eventName	事件名称，不同事件不能重名	是	[string]
        7	netType	wifi/4g/3g/3g/other	是	[string]
        8	channel	渠道值	是	[string]
        9	memberId	会员id，只有登录了才传		[int]
        10	url	H5/web的页面路径，不含域名部分		[string]
        11	appVersion	版本号,如1.0.0	是	[string]
        12	osVersion	系统版本，app传，其他可不传		[string]
        13	ip	H5/web可传，其他端可不传		[string]
        14	isDebug	1为测试数据，0为正式，测试版传1，正式版传0	是	[int]
        15	longitude	经度，如116.2345		[double]
        16	latitude	纬度,如26.3240	是	[string]
         */
        map.put("session", getSession(mActivity));
        map.put("deviceId", DeviceUtils.getAndroidId(mActivity, getUUid(mActivity)));
        map.put("deviceType", 2);
        map.put("deviceModel", android.os.Build.BRAND + " " + android.os.Build.MODEL);
        map.put("pageName", pageName);
        map.put("eventName", eventName);
        map.put("netType", DeviceUtils.getNetworkType(mActivity) == 1 ? "wifi" : "4g");
        map.put("channel", DataHelper.getStringSF(mActivity, DataHelperTags.CHANNEL));

        if (getMemberId(mActivity) > 0) {
            map.put("memberId", getMemberId(mActivity));
        }

//        map.put("url", 1);
        map.put("appVersion", DeviceUtils.getVersionName(mActivity));
        map.put("osVersion", android.os.Build.VERSION.RELEASE);
//        map.put("ip", 1);
        map.put("isDebug", BuildConfig.IS_PROD ? 0 : 1);
//        map.put("longitude", 1);
//        map.put("latitude", 1);
        return map;
    }

    public String getSession(Activity mActivity) {
        Calendar calendar = Calendar.getInstance();  //获取当前时间，作为图标的名字
        String year = calendar.get(Calendar.YEAR) + "";
        String month = calendar.get(Calendar.MONTH) + 1 + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        String timeInt = year + (Integer.valueOf(month) > 9 ? month : "0" + month) + day;

        if (Integer.valueOf(timeInt) > Integer.valueOf(DataHelper.getIntergerSF(mActivity, DataHelperTags.TIME_UUID_DAY))) {
            DataHelper.setIntergerSF(mActivity, DataHelperTags.TIME_UUID_DAY, Integer.valueOf(timeInt));

            String uuid = UUID.randomUUID().toString();
            DataHelper.setStringSF(mActivity, DataHelperTags.TIME_UUID, uuid);
        }
        return DataHelper.getStringSF(mActivity, DataHelperTags.TIME_UUID);
    }

    public String getUUid(Activity mActivity) {
        String uuid;
        if (DataHelper.contains(mActivity, DataHelperTags.UUID)) {//存在
            uuid = DataHelper.getStringSF(mActivity, DataHelperTags.UUID);
        } else {//不存在
            uuid = UUID.randomUUID().toString();
            DataHelper.setStringSF(mActivity, DataHelperTags.UUID, uuid);
        }
        return uuid;
    }

    public int getMemberId(Activity mActivity) {
        if (!DataHelper.getBooleanSF(mActivity, IS_LOGIN_SUCCESS)) {
            return 0;
        }
        UserInfoDetailsEntity entity = new Gson().fromJson(DataHelper.getStringSF(mActivity, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
        if (entity == null) return 0;

        return entity.getMemberId();

    }
}
