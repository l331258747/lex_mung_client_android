package com.lex_mung.client_android.app;

import android.annotation.SuppressLint;
import android.content.Context;

import com.lex_mung.client_android.mvp.model.entity.VersionEntity;
import com.lex_mung.client_android.mvp.ui.dialog.ForcedUpdateDialog;
import com.lex_mung.client_android.mvp.ui.dialog.InstallApkDialog;
import com.lex_mung.client_android.mvp.ui.dialog.SilentUpdateDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.zl.mvp.utils.AppUtils;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.DownloadConfig;
import zlc.season.rxdownload3.core.Downloading;
import zlc.season.rxdownload3.core.Failed;
import zlc.season.rxdownload3.core.Mission;
import zlc.season.rxdownload3.core.Normal;
import zlc.season.rxdownload3.core.Status;
import zlc.season.rxdownload3.core.Succeed;
import zlc.season.rxdownload3.core.Suspend;
import zlc.season.rxdownload3.core.Waiting;
import zlc.season.rxdownload3.database.SQLiteActor;
import zlc.season.rxdownload3.extension.ApkInstallExtension;
import zlc.season.rxdownload3.extension.ApkOpenExtension;

import static zlc.season.rxdownload3.helper.UtilsKt.dispose;

public class DownloadUtils {
    private Context mContext;
    private String url;
    private boolean isSilentDownload = false;//是否是静默下载
    private Disposable disposable;
    private Status currentStatus = new Status();

    private ForcedUpdateDialog forcedUpdateDialog;
    private SilentUpdateDialog silentUpdateDialog;
    private InstallApkDialog installApkDialog;

    @SuppressLint("StaticFieldLeak")
    private volatile static DownloadUtils INSTANCE;

    public static DownloadUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (DownloadUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DownloadUtils();
                }
            }
        }
        return INSTANCE;
    }

    private void initDownloadConfig(boolean flag) {
        DownloadConfig.INSTANCE.init(DownloadConfig.Builder.Companion.create(mContext)
                .setDefaultPath(AppUtils.obtainAppComponentFromContext(mContext).cacheFile().getAbsolutePath())
                .enableDb(true)
                .setDebug(true)
                .setDbActor(new SQLiteActor(mContext))
                .enableNotification(flag)
                .addExtension(ApkInstallExtension.class)
                .addExtension(ApkOpenExtension.class));
    }

    private void initDisposable() {
        disposable = RxDownload.INSTANCE.create(new Mission(url, "绿豆圈.apk"), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                    currentStatus = status;
                    setProgress(status);
                });
    }

    private void dispatchClick() {
        if (currentStatus instanceof Normal) {
            start();
        } else if (currentStatus instanceof Suspend) {
            start();
        } else if (currentStatus instanceof Failed) {
            start();
        } else if (currentStatus instanceof Succeed) {
            install();
        }
    }

    private void setProgress(Status status) {
        String text = "";
        if (status instanceof Normal) {
            text = "开始";
        } else if (status instanceof Suspend) {
            text = "已暂停";
        } else if (status instanceof Waiting) {
            text = "等待中";
        } else if (status instanceof Downloading) {
            text = "下载中";
        } else if (status instanceof Failed) {
            text = "失败";
        } else if (status instanceof Succeed) {
            text = "安装";
            if (isSilentDownload) {
                if (installApkDialog == null) {
                    installApkDialog = new InstallApkDialog(mContext, this::install);
                }
                if (!installApkDialog.isShowing()) {
                    installApkDialog.show();
                }
            } else {
                install();
            }
        } else if (status instanceof ApkInstallExtension.Installing) {
            text = "安装中";
        } else if (status instanceof ApkInstallExtension.Installed) {
            text = "打开";
        }
        if (forcedUpdateDialog != null) {
            forcedUpdateDialog.setProgress((int) status.getTotalSize(), (int) status.getDownloadSize(), status.percent());
            forcedUpdateDialog.setSize(status.formatString());
            forcedUpdateDialog.setStatus(text);
        }
        if (silentUpdateDialog != null) {
            silentUpdateDialog.setProgress((int) status.getTotalSize(), (int) status.getDownloadSize(), status.percent());
            silentUpdateDialog.setSize(status.formatString());
            silentUpdateDialog.setStatus(text);
        }
    }

    private void start() {
        RxDownload.INSTANCE.start(url).subscribe();
    }

    private void install() {
        RxDownload.INSTANCE.extension(url, ApkInstallExtension.class).subscribe();
    }

    /**
     * 更新版本
     *
     * @param context context
     * @param entity  VersionEntity
     */
    public void update(Context context, VersionEntity entity) {
        if (entity.getForceUpdate() == 1) {
            forcedUpdate(context, entity);
        } else {
            silentUpdate(context, entity);
        }
    }

    /**
     * 强制更新
     *
     * @param context context
     * @param entity  VersionEntity
     */
    private void forcedUpdate(Context context, VersionEntity entity) {
        mContext = context;
        url = entity.getDownloadUrl();

        initDownloadConfig(false);
        initDisposable();

        forcedUpdateDialog = new ForcedUpdateDialog(context, entity.getVersionName(), entity.getUpdateTips());
        forcedUpdateDialog.setOnClickListener(this::dispatchClick);
        forcedUpdateDialog.show();
    }

    /**
     * 静默更新
     *
     * @param context context
     * @param entity  VersionEntity
     */
    private void silentUpdate(Context context, VersionEntity entity) {
        mContext = context;
        url = entity.getDownloadUrl();

        initDownloadConfig(true);
        initDisposable();

        silentUpdateDialog = new SilentUpdateDialog(context, entity.getVersionName(), entity.getUpdateTips());
        silentUpdateDialog.setOnClickListener(new SilentUpdateDialog.OnClickListener() {
            @Override
            public void onCancel() {
                isSilentDownload = true;
                dispatchClick();
            }

            @Override
            public void onConfirm() {
                dispatchClick();
            }
        });
        silentUpdateDialog.show();
    }

    public void onDestroy() {
        if (disposable != null) {
            dispose(disposable);
        }
    }
}
