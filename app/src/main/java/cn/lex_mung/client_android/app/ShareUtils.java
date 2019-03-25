package cn.lex_mung.client_android.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.ui.dialog.EasyDialog;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DrawableProvider;

/**
 * 分享
 */
public class ShareUtils {


    public static void shareUrl(Activity mActivity, View view, String url, String title, String des) {
        shareUrl(mActivity, view, url, title, des, "");
    }

    @SuppressLint("InflateParams")
    public static void shareUrl(Activity mActivity, View view, String url, String title, String des, String avatar) {
        View layout = mActivity.getLayoutInflater().inflate(R.layout.layout_share_dialog, null);
        EasyDialog easyDialog = new EasyDialog(mActivity)
                .setLayout(layout)
                .setVisibility(View.GONE)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setBackgroundColor(AppUtils.getColor(mActivity, R.color.c_00))
                .setLocationByAttachedView(view)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 200, 1000, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 1000)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setMarginLeftAndRight(0, 0)
                .setOutsideColor(AppUtils.getColor(mActivity, R.color.c_50))
                .show();
        layout.findViewById(R.id.ll_wx_session).setOnClickListener(v -> {
            sendReq(mActivity, url, title, des, avatar, true);
            dismiss(mActivity, easyDialog);
        });
        layout.findViewById(R.id.ll_wx_timeline).setOnClickListener(v -> {

            sendReq(mActivity, url, title, des, avatar, false);
            dismiss(mActivity, easyDialog);
        });
        layout.findViewById(R.id.ll_copy).setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm != null) {
                cm.setText(url);
            }
            AppUtils.makeText(mActivity, "已复制到剪贴板");
            dismiss(mActivity, easyDialog);
        });
        layout.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss(mActivity, easyDialog));
    }

    private static void dismiss(Activity mActivity, EasyDialog easyDialog) {
        if (easyDialog != null
                && !mActivity.isFinishing()) {
            easyDialog.dismiss();
        }
    }

    @SuppressLint("CheckResult")
    private static void sendReq(Activity mActivity, String url, String title, String des, String avatar, boolean isSession) {
        if (!AppUtils.isWeixinAvilible(mActivity)) {
            AppUtils.makeText(mActivity, "微信未安装");
            return;
        }
        if (!TextUtils.isEmpty(avatar)) {
            Observable.create((ObservableOnSubscribe<String>) e -> e.onNext(avatar))
                    .map(DrawableProvider::getBitmap)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bp -> sendReq(mActivity, url, title, des, isSession, bp));
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.ic_launcher);
            sendReq(mActivity, url, title, des, isSession, bmp);
        }
    }

    private static void sendReq(Activity mActivity, String url, String title, String des, boolean isSession, Bitmap bp) {
        if (bp == null) {
            AppUtils.makeText(mActivity, "分享失败，请重试");
            return;
        }
        try {
            if (TextUtils.isEmpty(des)) {
                des = "做最精准的匹配，找最合适的律师";
            }
            IWXAPI api = WXAPIFactory.createWXAPI(mActivity, Constants.APP_ID, true);
            api.registerApp(Constants.APP_ID);
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = des;
            msg.thumbData = DrawableProvider.bmpToByteArray(bp, 32 * 1024);
            bp.recycle();
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = isSession ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            api.sendReq(req);
        } catch (Exception e) {
            AppUtils.makeText(mActivity, "分享失败，请重试");
        }
    }
}
