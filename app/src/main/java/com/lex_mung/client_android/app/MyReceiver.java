package com.lex_mung.client_android.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;
import me.zl.mvp.utils.AppUtils;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
           /* Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = manager.getNotificationChannel("default");
                    if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                        i.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        AppUtils.makeText(context, "请手动将通知打开");
                    }
                }
                String title;
                String content;
                Intent intent1 = new Intent(context, PushJumpActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (!bundle.getString(JPushInterface.EXTRA_MESSAGE).startsWith("{")) {
                    title = bundle.getString(JPushInterface.EXTRA_TITLE);
                    content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                } else {
                    JMessageEntity message = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_MESSAGE), JMessageEntity.class);
                    title = message.getTitle();
                    content = message.getContent();
                    intent1.putExtra(BundleTags.JSON, bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    if (message.getSubType() == 121) {
                        AppUtils.postString(REFRESH, REFRESH_DIALOG, message.getContent());
                    }
                }
                PendingIntent pendingIntent = PendingIntent.getActivity(context
                        , (int) System.currentTimeMillis()
                        , intent1
                        , PendingIntent.FLAG_UPDATE_CURRENT);
                AppUtils.post(REFRESH, REFRESH_UNREAD_MESSAGES_NUMBER);
                Notification notification = new NotificationCompat.Builder(context, "default")
                        .setContentTitle(title)
                        .setContentText(content)
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_logo_round))
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setFullScreenIntent(null, true)
                        .build();
                manager.notify(1, notification);
            }*/
        } catch (Exception ignored) {
        }
    }
}
