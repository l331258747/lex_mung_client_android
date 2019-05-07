package cn.lex_mung.client_android.app;

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

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.lex_mung.client_android.BuildConfig;
import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.utils.LogUtil;
import me.zl.mvp.base.delegate.AppLifecycles;
import me.zl.mvp.utils.DataHelper;
import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 需要在application中初始化的东西
 */
public class AppLifecyclesImpl implements AppLifecycles {
//    private final boolean isDebug = false;//正式环境为true  测试环境为false
    private final boolean isDebug = BuildConfig.IS_PROD;//正式环境为true  测试环境为false

    /**
     * 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
     */
    @Override
    public void attachBaseContext(@NonNull Context base) {
    }

    @Override
    public void onCreate(@NonNull Application application) {
//        if (BuildConfig.LOG_DEBUG) {//Timber初始化
//            Timber.plant(new Timber.DebugTree());
//            ButterKnife.setDebug(isDebug);
//        }
        if (!BuildConfig.IS_PROD) {//Timber初始化 测试环境打印log
            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(isDebug);
        }

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.e( " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                LogUtil.e(" onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(application,  cb);


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
        UMConfigure.init(application, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(!isDebug);

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
