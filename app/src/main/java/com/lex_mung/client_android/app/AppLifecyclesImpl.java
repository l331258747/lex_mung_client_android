package com.lex_mung.client_android.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.lex_mung.client_android.BuildConfig;
import com.lex_mung.client_android.R;
import com.lex_mung.client_android.mvp.model.entity.DeviceEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import me.zl.mvp.base.delegate.AppLifecycles;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.DeviceUtils;
import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 需要在application中初始化的东西
 */
public class AppLifecyclesImpl implements AppLifecycles {
    private final boolean isDebug = false;//正式环境为true  测试环境为false

    @Override
    public void attachBaseContext(@NonNull Context base) {
    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (BuildConfig.LOG_DEBUG) {//Timber初始化
            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(isDebug);
        }

        ApplicationInfo appInfo;
        String channel = "website";
        try {
            appInfo = application.getPackageManager().getApplicationInfo(application.getPackageName(),
                    PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("JPUSH_CHANNEL");
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        DataHelper.setStringSF(application, DataHelperTags.CHANNEL, channel);

        //极光
        JPushInterface.setDebugMode(isDebug);
        JPushInterface.setChannel(application, channel);
        JPushInterface.init(application);
        JMessageClient.setDebugMode(isDebug);
        JMessageClient.init(application, true);

        //友盟
        UMConfigure.init(application, "5a54af3cf29d987c01000186", channel, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(!isDebug);
        MobclickAgent.setScenarioType(application, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setSessionContinueMillis(1000 * 30);

        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.c_f4f4f4, R.color.c_b5b5b5);
            return new ClassicsHeader(context);
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));
        createNotificationChannel(application);

        CrashReport.initCrashReport(application, "d35b524e94", isDebug);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    private void createNotificationChannel(Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "default", NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            NotificationManager notificationManager = (NotificationManager) application.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
