package com.lex_mung.client_android.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lex_mung.client_android.app.BundleTags;
import com.lex_mung.client_android.app.DataHelperTags;
import com.lex_mung.client_android.app.EventBusTags;
import com.lex_mung.client_android.app.PayStatusTags;
import com.lex_mung.client_android.mvp.ui.activity.PayStatusActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;

import static com.lex_mung.client_android.app.DataHelperTags.APP_ID;
import static com.lex_mung.client_android.app.DataHelperTags.ORDER_NO;
import static com.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH;
import static com.lex_mung.client_android.app.EventBusTags.REFRESH.REFRESH_WX_PAY;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String appId = DataHelper.getStringSF(this, APP_ID);
        IWXAPI api = WXAPIFactory.createWXAPI(this, appId, true);
        api.registerApp(appId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (DataHelper.getIntergerSF(this, DataHelperTags.PAY_TYPE) == PayStatusTags.FAST_CONSULT
                && baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
            AppUtils.post(REFRESH, REFRESH_WX_PAY);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(BundleTags.ORDER_NO, DataHelper.getStringSF(this, ORDER_NO));
            bundle.putInt(BundleTags.WX, baseResp.errCode);
            Intent intent = new Intent(this, PayStatusActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        finish();
    }
}
